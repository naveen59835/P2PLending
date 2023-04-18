/*
 * Author : Naveen Kumar
 * Date : 13-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.notifications.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "contactus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactUs {
    private String name;
    private String phone;
    private String email;
    private String message;
}
