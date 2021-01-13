package com.ktg.usuarioSpring.security.services;

import com.ktg.usuarioSpring.dao.IUsuarioDao;
import com.ktg.usuarioSpring.model.entity.Usuario;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Log
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.getUsuarioLogin(s);
        log.log(Level.INFO,"UserDetailsServiceImp " + usuario);
        User.UserBuilder builder = null;

        if (usuario != null){
            builder = User.withUsername(s);
            builder.disabled(false);
            builder.password(usuario.getPassword());
        }else{
            return null;
        }
        return UserDetailsImpl.build(usuario);
    }
}
