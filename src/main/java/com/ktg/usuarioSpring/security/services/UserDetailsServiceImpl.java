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
    //Obtiene el usuario por medio del correo y devuelve un UserDetails que es un
    //objeto que Spring Security puede usar para autenticación y validación.
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.getUsuarioLogin(correo);
        log.log(Level.INFO,"UserDetailsServiceImp " + usuario);
        User.UserBuilder builder = null;

        if (usuario != null){
            builder = User.withUsername(correo);
            builder.disabled(false);
            builder.password(usuario.getPassword());
        }else{
            return null;
        }
        return UserDetailsImpl.build(usuario);
    }
}
