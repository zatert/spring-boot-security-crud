package ru.zatert.springbootsecuritycrud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zatert.springbootsecuritycrud.entities.Role;
import ru.zatert.springbootsecuritycrud.entities.User;
import ru.zatert.springbootsecuritycrud.repositories.RoleRepo;
import ru.zatert.springbootsecuritycrud.repositories.UserRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername (String username){
        return userRepo.findByUsername(username);
    }
//    public List<User> findAll () {return userRepo.findAll();}
//    public void deleteById (Long id) {userRepo.deleteById(id);}
//    public void addUser (User user){userRepo.save(user);}
   // public User findById(Long id){return userRepo.findById(id).get();}
//    public void save(User user){userRepo.save(user);}

    /*---------========================== GET ONE =========================------------------*/
    public ResponseEntity<User> getUser(Long id) {
        try{
            return ResponseEntity.ok().body(userRepo.findById(id).get());
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    /*---------========================== GET ALL =========================------------------*/
    public ResponseEntity<List<User>> getAll(){
        List<User> list = new ArrayList<>();
        userRepo.findAll().forEach(list::add);
        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(list);
    }
    /*---------========================== ADD =========================------------------*/
    public ResponseEntity<User> saveUser(User user){
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        Long roleId = Long.parseLong( user.getLastname()); //это id роли на самом деле
        Role role = roleRepo.findById(roleId).get();
        Collection <Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok().body(user); //<>(user, HttpStatus.CREATED);
    }
    /*---------========================== EDIT =========================------------------*/
    public ResponseEntity<User> updateUser(Long id, User user){
        User currentUser = userRepo.findById(id).get();
        if(user == null){
            return ResponseEntity.badRequest().build();
        }
        Long roleId = Long.parseLong( user.getLastname()); // это id роли на самом деле
        Role role = roleRepo.findById(roleId).get();
        currentUser.setId(id);
        Collection <Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        currentUser.setUsername(user.getUsername());
       currentUser.setRoles(user.getRoles());
        currentUser.setEmail(user.getEmail());
//        userDao.edit(currentUser);
        userRepo.save(currentUser);
        return ResponseEntity.ok().body(currentUser); //currentUser user
    }
    /*---------========================== DELETE =========================------------------*/
    public ResponseEntity<User> deleteUser(Long id){
        User user = userRepo.findById(id).get();
        if(user == null){
            return ResponseEntity.notFound().build();
        }
       userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s not found", username ));
        }
        // Приводим нашего юзера к ЮзерДетэйлзу, последний параметр коллекция прав доступа
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    // из коллекции ролей получает коллекцию прав доступа
    private Collection <? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        // берет пачку ролей и делает пачку Ауторитез с точно такими же строками
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
