package com.stackroute.recommendation.domain;

import lombok.*;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
//import org.neo4j.ogm.annotation.Id;
//import org.neo4j.ogm.annotation.NodeEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Node("cibil")
public class CibilScore{
    @Id
   private String score;
}
