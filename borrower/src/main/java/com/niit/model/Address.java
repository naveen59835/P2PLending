/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Address {
    String address;

    String city;

    String pin;

    String state;
}
