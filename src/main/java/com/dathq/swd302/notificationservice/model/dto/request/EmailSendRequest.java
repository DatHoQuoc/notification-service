package com.dathq.swd302.notificationservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailSendRequest {
    @NotBlank(message = "Người nhận không được để trống")
    @Email(message = "Định dạng email không hợp lệ")
    private String to;

    @NotBlank(message = "Tiêu đề không được để trống")
    private String subject;

    @NotBlank(message = "Nội dung không được để trống")
    private String body;
}

