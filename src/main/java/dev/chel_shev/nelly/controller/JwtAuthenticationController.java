package dev.chel_shev.nelly.controller;

//@RestController
//@CrossOrigin
//@RequiredArgsConstructor
//public class JwtAuthenticationController {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthenticationManager authenticationManager;
//    private final UserService userService;
//
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @ResponseBody
//    public ResponseEntity<?> login(String username, String password) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            UserEntity userEntity = userService.getUserByName(username);
//            if (isNull(userEntity))
//                throw new UsernameNotFoundException("User not found!");
//            String token = jwtTokenProvider.createToken(username);
//            return ResponseEntity.ok(new JwtResponse(username, token));
//            return null;
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//    }
//}
