package com.example.authnamansir.services;


import com.example.authnamansir.dtos.UserDto;
import com.example.authnamansir.exceptions.UserAlreadyExistsException;
import com.example.authnamansir.exceptions.UserDoesNotExistException;
import com.example.authnamansir.models.Session;
import com.example.authnamansir.models.SessionStatus;
import com.example.authnamansir.models.User;
import com.example.authnamansir.repositories.SessionRepository;
import com.example.authnamansir.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private final SessionRepository sessionRepository;
    // private SessionRepository sessionRepository;

    public AuthServiceImpl( UserRepository userRepository,
                            SessionRepository sessionRepository) {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
       // this.sessionRepository = sessionRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public UserDto signUp(String email, String password) throws UserAlreadyExistsException {

        Optional<User> userOptional= userRepository.findByEmail(email);

        if(!userOptional.isEmpty()){
            throw new UserAlreadyExistsException("User with "+email+" already exists.");
        }
        User user=new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser=userRepository.save(user);
        return UserDto.from(savedUser);
    }

    @Override
    public ResponseEntity<UserDto> login(String email,String password) throws UserDoesNotExistException {
        Optional<User> userOptional=userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            throw new UserDoesNotExistException("User with email: "+email+" doesn't exist.");
        }
        User user=userOptional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //TODO: Update here to use JWT
        //Payload: {userId:
        //          email:
        //          roles:[]
        //          }
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("email",user.getEmail());
        claims.put("roles",user.getRoles());

         String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

        String jwt= Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512,secret)
                .compact();
        String token= RandomStringUtils.randomAscii(20);

        MultiValueMapAdapter<String,String> headers=new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN",jwt);

        Session session=new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(jwt);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto=UserDto.from(user);
        ResponseEntity<UserDto> response=new ResponseEntity<>(
                userDto,
                headers,
                HttpStatus.OK);

        return response;
    }

    @Override
    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUserId(token,userId);
        //return null;

        if(sessionOptional.isEmpty()){
            return SessionStatus.INVALID;
        }

        Session session=sessionOptional.get();

        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.EXPIRED;
        }

//        if(!session.getExpiringAt()>new Date()){
//            return SessionStatus.EXPIRED;
//        }

        return SessionStatus.ACTIVE;
    }

    @Override
    public ResponseEntity<Void> logout(String token, Long userId) {

        Optional<Session> sessionOptional=sessionRepository.findByTokenAndUserId(token, userId);

        if(sessionOptional.isEmpty()){
            return null;
        }

        Session session=sessionOptional.get();

        session.setSessionStatus(SessionStatus.EXPIRED);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }
}
