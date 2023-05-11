package com.talkeasy.server.domain.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.locationtech.jts.geom.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@SequenceGenerator(name = "LOCATION_GENERATOR", sequenceName = "MY_SEQUENCES", allocationSize = 1)
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_GENERATOR")
    private Long id;
    @Column
    private String userId;
    @Column
    private String name;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point geom;
    @Column
    private LocalDateTime dateTime;

}
