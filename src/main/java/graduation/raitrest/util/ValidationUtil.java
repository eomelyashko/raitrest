package graduation.raitrest.util;


import graduation.raitrest.model.entities.Role;
import graduation.raitrest.model.entities.User;
import graduation.raitrest.model.to.HasId;
import graduation.raitrest.model.to.UserTo;
import graduation.raitrest.util.exception.IllegalRequestDataException;
import graduation.raitrest.util.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.StringJoiner;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static final LocalTime MAX_TIME = LocalTime.of(11, 00);
    public static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(LocalDate.now(), MAX_TIME);

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
     //   return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
        return  e.getClass().getName() + ": "+ e.getMessage();
    }

    public static void checkDateTime(LocalDateTime dateTime) {
        if (dateTime.isAfter(MAX_DATE_TIME)) {
            throw new IllegalArgumentException("Time must be before " + MAX_TIME);
        }
    }

    public static void checkRoleForProfile(UserTo userTo) {
        //  only roles(user and manager) can be created, not admin
        if (userTo.getRoles().contains(Role.ROLE_ADMIN))
            throw new IllegalRequestDataException("Only roles(user and manager) can be created, not admin");
    }

    public static ResponseEntity<String> getErrorResponse(BindingResult result) {
        StringJoiner joiner = new StringJoiner("<br>");
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (msg != null) {
                        if (!msg.startsWith(fe.getField())) {
                            msg = fe.getField() + ' ' + msg;
                        }
                        joiner.add(msg);
                    }
                });
        return ResponseEntity.unprocessableEntity().body(joiner.toString());
    }

    private static final Validator validator;

    static {
        //  From Javadoc: implementations are thread-safe and instances are typically cached and reused.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //  From Javadoc: implementations of this interface must be thread-safe
        validator = factory.getValidator();
    }

    public static <T> void validate(T bean) {
        // https://alexkosarev.name/2018/07/30/bean-validation-api/
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}