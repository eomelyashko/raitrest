package graduation.raitrest.web;


import graduation.raitrest.AuthorizedUser;
import graduation.raitrest.model.AbstractBaseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.Objects.requireNonNull;

public class SecurityUtil {


    private SecurityUtil() {
    }

    //    public static int authUserId() {
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof AuthorizedUser)
//           return  ((AuthorizedUser)principal).getId();
//       return id; // return 0;
//    }
    public static int authUserId() {
        return get().getUserTo().id();
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }




}