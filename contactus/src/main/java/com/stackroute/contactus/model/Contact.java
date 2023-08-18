/*
 * Author : Naveen Kumar
 * Date : 29-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.contactus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contactus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Contact {
    @Id
    private String id;
    private String email;
    private String message;
}
