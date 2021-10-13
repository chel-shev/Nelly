package dev.chel_shev.nelly.inquiry;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.type.KeyboardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryAnswer {

    private UserEntity user;
    private String message;
    private KeyboardType keyboardType;
}
