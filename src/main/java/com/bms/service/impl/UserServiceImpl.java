package com.bms.service.impl;


import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bms.entity.EmailCode;
import com.bms.entity.Otp;
import com.bms.entity.User;
import com.bms.repository.EmailCodeRepository;
import com.bms.repository.OtpRepository;
import com.bms.repository.UserRepository;
import com.bms.requestdto.ChangePasswordRequest;
import com.bms.requestdto.LoginRequest;
import com.bms.requestdto.LoginWIthCode;
import com.bms.requestdto.NumberVerificationRequest;
import com.bms.requestdto.OtpRequest;
import com.bms.requestdto.SignupRequest;
import com.bms.responsedto.LoginResponse;
import com.bms.responsedto.Response;
import com.bms.responsedto.UserResponse;
import com.bms.service.UserService;
import com.bms.util.CodeMailSender;
import com.bms.util.GenerateOtp;
import com.bms.util.JWTTokenUtil;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 6998248696972809679L;
	private final UserRepository userRepository;
	private final OtpRepository otpRepository;
	private final EmailCodeRepository emailCodeRepository;
	private final GenerateOtp generateOtp;
	private final CodeMailSender codeMailSender;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenUtil jwtTokenUtil;

	@Override
	public Response<String> sendOtp(OtpRequest request) {
		System.out.println(request.getPhoneNumber());
		if(!StringUtils.hasText(request.getPhoneNumber())) {
			return new Response<>(HttpStatus.SC_BAD_REQUEST,"Phone Number is Not valid");
		}
		String regEx = "^([987]{1})(\\d{1})(\\d{8})";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(request.getPhoneNumber());
		if(!matcher.matches()) {
			return new Response<>(HttpStatus.SC_BAD_REQUEST,"Phone Number is Not valid");
		}
		Optional<User> user = userRepository.findByPhoneNumber(request.getPhoneNumber());
		if(!user.isEmpty()) {
			if(user.get().isActive()){
			   return new Response<>(HttpStatus.SC_CONFLICT,"Phone Number Already Exists.Try Login!");
			}
		}
		Otp otp = new Otp();
		Optional<Otp> otpOptional = otpRepository.findByPhoneNumber(request.getPhoneNumber());
		String oneTimePassword = generateOtp.generateOtp().get().toString();
		if(!otpOptional.isEmpty()) {
			final long OTP_VALID_DURATION = 5 * 60 * 1000;
			 long currentTimeInMillis = System.currentTimeMillis();
			 long otpRequestedTimeInMillis = otpOptional.get().getGeneratedTime().getTime();
			 if(otpOptional.get().getOtpCount()>5 && otpRequestedTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis  ) {
					return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"OTP limit exceed!! Try after 5 mins");
				}
			 otp.setOtpCount(otpOptional.get().getOtpCount()+1);
		}else {
			 otp.setOtpCount(otp.getOtpCount()+1);
		}
		otp.setOtp(oneTimePassword);
		otp.setPhoneNumber(request.getPhoneNumber());
		otp.setGeneratedTime(new Date());
		otpRepository.save(otp);
		return new Response<>("OTP send.");
	}

	@Override
	public Response<String> verifyPhoneNumber(NumberVerificationRequest request) {
		if(!StringUtils.hasText(request.getPhoneNumber()) || !StringUtils.hasText(request.getOtp())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Otp");
		}
		Optional<Otp> otp = otpRepository.findByPhoneNumber(request.getPhoneNumber());
		if(otp.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Otp");
		}
		Optional<User> user = userRepository.findByPhoneNumber(request.getPhoneNumber());
		if(!user.isEmpty()) {
			if(user.get().isActive()) {
				otp.get().setOtpCount(0);
				otp.get().setOtp(null);
				otpRepository.save(otp.get());
				return new Response<>(HttpStatus.SC_OK,"Phone Number Already Exists! Try Login");
			}
			if(user.get().isPhoneNumberVerified()) {
				otp.get().setOtpCount(0);
				otp.get().setOtp(null);
				otpRepository.save(otp.get());
				return new Response<>(HttpStatus.SC_OK,"Phone Number Already Verified!");
			}
		}
		if(otp.get().getOtp()==null) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Invalid Otp");
		}
		if(otp.get().getOtp().equals(request.getOtp())) {
			 final long OTP_VALID_DURATION = 5 * 60 * 1000;
			 long currentTimeInMillis = System.currentTimeMillis();
			 long otpRequestedTimeInMillis = otp.get().getGeneratedTime().getTime();
			 if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
				 return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"OTP Expired!!");
		     }
			 User newuser = new User();
			 newuser.setPhoneNumber(request.getPhoneNumber());
			 newuser.setActive(false);
			 newuser.setPhoneNumberVerified(true);
			 otp.get().setOtpCount(0);
			 otp.get().setOtp(null);
			 otpRepository.save(otp.get());
			 userRepository.save(newuser);
			 return new Response<>("Phone Number Verified!");
		}else {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Otp");
		}
	}

	@Override
	public Response<UserResponse> signup(SignupRequest request) {
        Optional<User> user = userRepository.findByPhoneNumber(request.getPhoneNumber());
        Optional<User> emailUser = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty()) {
        	return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Phone Number");
        }
        if(user.get().isActive()) {
        	return new Response<>(HttpStatus.SC_OK,"User Already Created for this Phone Number!");
        }
        if(!emailUser.isEmpty()) {
        	return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Email already exists!");
        }
        user.get().setFirstName(request.getFirstName());
        user.get().setLastName(request.getLastName());
        LocalDate dob = request.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        user.get().setDob(dob);
        user.get().setActive(true);
        user.get().setEmail(request.getEmail());
        user.get().setCountry(request.getCountry());
        user.get().setPassword(passwordEncoder.encode(request.getPassword().trim()));
        User newUser = userRepository.save(user.get());
        Date date = Date.from(newUser.getDob().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        UserResponse response= new UserResponse(newUser.getId(),newUser.getFirstName(),newUser.getLastName(),newUser.getDob(),newUser.getPhoneNumber(),
        				newUser.getEmail(),newUser.getCountry(),newUser.isPhoneNumberVerified(),newUser.isActive(),newUser.getCreatedAt());
        return new Response<>(response);
	}

	@Override
	public Response<LoginResponse> login(LoginRequest request, String ipAddress) {
		
	    if(!StringUtils.hasText(request.getPhoneNumber()) || !StringUtils.hasText(request.getPassword())) {
	    	return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Credentials");
	    }
	    Optional<User> user = userRepository.findByPhoneNumber(request.getPhoneNumber());
	    if(user.isEmpty()) {
	    	return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"User Not Found!");
	    }
	    if(!user.get().isActive()) {
	    	return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"User Not Found!");
	    }
	    if(passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
	    	LoginResponse response = new LoginResponse(jwtTokenUtil.generateToken(user.get().getPhoneNumber(), user.get().getId(), user.get().getEmail(), ipAddress));
	    	return new Response<>(response);
	    }else {
	    	return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Invalid Credentials");
	    }
	}
	
	@Override
	public Response<UserResponse> getUserDetails(String userId){
		Optional<User> user = userRepository.findById(userId);
		if(user.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"User Not Found");
		}
		if(!user.get().isActive()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"User Not Found");
		}
       // Date date = Date.from(user.get().getDob().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		return new Response<>(new UserResponse(user.get().getId(),user.get().getFirstName(),user.get().getLastName(),
				user.get().getDob(),user.get().getPhoneNumber(),user.get().getEmail(),user.get().getCountry(),
				user.get().isPhoneNumberVerified(),user.get().isActive(),user.get().getCreatedAt()));
	}

	@Override
	public Response<LoginResponse> loginWithCode(LoginWIthCode request, String ipAddress) {
		if(!StringUtils.hasText(request.getEmail()) || !StringUtils.hasText(request.getCode())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Code");
		}
		Optional<User> user = userRepository.findByEmail(request.getEmail());
		if(user.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Email");
		}
		
		if(!user.get().isActive()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Email");
		}
		Optional<EmailCode> emailCode = emailCodeRepository.findById(request.getEmail());
		if(emailCode.isEmpty() || emailCode.get().getCode()==null) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Code");
		}
		if(request.getCode().equals(emailCode.get().getCode())) {
			LoginResponse response = new LoginResponse(
					jwtTokenUtil.generateToken(user.get().getPhoneNumber(), user.get().getId(), user.get().getEmail(), ipAddress));
			emailCode.get().setCode(null);
			emailCode.get().setCodeCount(0);
			emailCodeRepository.save(emailCode.get());
			return new Response<>(response);
		}else {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Invalid Code");
		}
	}

	@Override
	public Response<String> forgetPassword(String email) throws UnsupportedEncodingException, MessagingException {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isEmpty()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Email not exists!");
		}
		if(!user.get().isActive()) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Email not exists");
		}
		EmailCode code = new EmailCode();
		Optional<EmailCode> codeOptional = emailCodeRepository.findById(email);
		String oneTimeCode = generateOtp.generateCode();
		if(!codeOptional.isEmpty()) {
			 final long OTP_VALID_DURATION = 5 * 60 * 1000;
			 long currentTimeInMillis = System.currentTimeMillis();
			 long otpRequestedTimeInMillis = codeOptional.get().getGeneratedTime().getTime();
			if(codeOptional.get().getCodeCount()>5 && otpRequestedTimeInMillis + OTP_VALID_DURATION > currentTimeInMillis  ) {
				return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Code limit exceed!! Try after 5 mins");
			}
			code.setCodeCount(codeOptional.get().getCodeCount() + 1 );
		}else {
			code.setCodeCount(code.getCodeCount() + 1);
		}
		code.setCode(oneTimeCode);
		code.setEmail(email);
		code.setGeneratedTime(new Date());
		String userName = user.get().getFirstName()+" "+user.get().getLastName();
		String isMailSend = codeMailSender.sendCode(userName, email, oneTimeCode);
		emailCodeRepository.save(code);
		return new Response<>(isMailSend);
	}

	@Override
	public Response<String> changePassword(String userId, ChangePasswordRequest request) {
		Optional<User> userOptional = userRepository.findById(userId);
		if(userOptional.isEmpty()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"UnAuthorized");
		}
		if(!userOptional.get().isActive()) {
			return new Response<>(HttpStatus.SC_FORBIDDEN,"User Not Found!");
		}
		if(!StringUtils.hasText(request.getNewPassword())) {
			return new Response<>(HttpStatus.SC_INTERNAL_SERVER_ERROR,"Make Strong Password!");
		}
		userOptional.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(userOptional.get());
		return new Response<>(HttpStatus.SC_OK,"Password Changed");
	}
}
