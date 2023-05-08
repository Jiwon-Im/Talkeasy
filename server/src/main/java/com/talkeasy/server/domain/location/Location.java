package com.talkeasy.server.domain.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SequenceGenerator(name = "LOCATION_GENERATOR", sequenceName = "MY_SEQUENCES", allocationSize = 1)
public class Location {

    // TODO : Geometry로 변경
    @Column
    String email;
    @Column
    String x;
    @Column
    String y;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_GENERATOR")
    private Long id;
    @Column
    LocalDateTime dateTime;


}