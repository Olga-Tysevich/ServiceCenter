package it.academy.service.dto.validator;

import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.stream.Collectors;

import static it.academy.service.utils.UIConstants.ERROR_MESSAGE;

@UtilityClass
public class DtoValidator {

    public static <T> boolean isValid(Model model, T dto, String paramName, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrors(bindingResult);
            model.addAttribute(ERROR_MESSAGE, error);
            model.addAttribute(paramName, dto);
            return false;
        }
        return true;
    }

    public static String getErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
    }

}
