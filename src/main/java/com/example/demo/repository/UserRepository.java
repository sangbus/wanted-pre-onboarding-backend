//package com.example.demo.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByMemberId(String email);
//
////    private final JdbcTemplate jdbcTemplate;
////
////    @Autowired
////    public UserRepository(JdbcTemplate jdbcTemplate) {
////        this.jdbcTemplate = jdbcTemplate;
////    }
////
////    public User save(User user){
////        String sql = "insert into user (email, password) values (?,?)";
////        int result = jdbcTemplate.update(sql, user.getEmail(), user.getPassword());
////        log.info(result+"줄 성공");
////        return user;
////    }
//}
