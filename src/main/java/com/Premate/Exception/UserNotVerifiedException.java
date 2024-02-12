package com.Premate.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotVerifiedException extends RuntimeException {
 private String msg;
}
