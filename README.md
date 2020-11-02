# Online Art Gallery
[![Build Status](https://travis-ci.com/McGill-ECSE321-Fall2020/project-group-07.svg?token=uVygPvq8RcZYse4EpgeN&branch=master)](https://travis-ci.com/McGill-ECSE321-Fall2020/project-group-07)

## Project Overview
This project develops an Online Gallery System to help a Montreal gallery promote local artists during the pandemic. In this system, artists create their personal profiles and upload their artworks. Each day, artists receive an update on the total number of views on their artworks by customers. Customers can browse available artworks either by artist, randomly, or by chronological order. Once a customer purchases an artwork, they then have the option of specifying a delivery to their home or a pickup at the physical address of the gallery itself. The administrator of the Online Gallery System has access to a log of all past purchases and shipments for record-keeping and also troubleshooting.

### About us
The team is composed of 5 McGill University students within the Faculty of Engineering.

| Name | GitHub | Major | Year |
| ------------- | ------------- | ------------- | ------------- |
|Anthony Dagher | [anthonydagher](https://github.com/anthonydagher) | Software Engineering | U2 |
|Jay Han | [jhanmtl](https://github.com/jhanmtl) | Mechanical Engineering, SE Minor | U4 |
|Massimo Vadacchino  | [MassFC](https://github.com/MassFC) | Software Engineering | U2 |
|Keon Olsz| [KeonOlszewski-ma](https://github.com/KeonOlszewski) | Software Engineering | U2 |
|Natalia Tabet | [natis5005](https://github.com/natis5005) | Electrical Engineering | U3 |

## Overview Tables
### Project
| NAME                   | ROLE |  SPRINT 1 (Hours) | SPRINT 2 (Hours) | SPRINT 3 (Hours) | SPRINT 4 (Hours)|
|------------------------|------|-----------|----------|----------|----------|
| Anthony Dagher         | Software Developer       | 20  |  30 |   |          |
| Jay Han                |Project Manager, Developer| 35  |  45 |   |          |
| Keon Olsz              | Software Developer       | 20 |  25 |   |          |
| Massimo Vadacchino     | CI lead, Developer       | 30  |  32 |   |          |
| Natalia Tabet          | Software Developer       | 26  |   30 |   |          |

### Sprint 1

The project report for Sprint 1 can be found [here](https://github.com/McGill-ECSE321-Fall2020/project-group-07/wiki/Project-Report-(Sprint-1)).

| Name | Contributions | Hours |
| ------------- | ------------- | ------------- |
| Anthony Dagher| <ins>Persistence tests for _OnlineGallery, Artist_ classes</ins>, CrudRepository interfaces for both classes, 3 individual requirements, 3 use cases, 1 activity diagram, 1 individual domain model, collect & organize final 15 requirements, re-phrase final list of requirements & use cases, finalize wiki|20|
| Jay Han| Gradle setup, TravisCI setup, Heroku database setup, <ins>persistence tests for _GalleryRegistration, Purchase_ classes</ins>, CrudRepository interfaces for both classes, 3 individual requirements, 3 use cases, 1 activity diagram, 1 individual domain model,git management, JPA annotation corrections |35|
| Keon Olsz |<ins>Persistece tests for _GalleryAdmin, Artwork_ classes</ins>, CrudRepository interfaces for both classes, 3 individual requirements, 3 use cases, 1 activity diagram, 1 individual domain model, collect & organize final 15 requirements, CrudRepository classes for both respective tests |20|
 |Massimo Vadacchino| UMLLab code generation, project wiki setup, Readme.md setup, project report, <ins>persistence tests for _Profile, Shipment_ classes</ins>, CrudRepository interfaces for both classes, 3 individual requirements, 3 use cases, 1 activity diagram, 1 individual domain model, re-phrase final list of requirements, merged/finalized domain model, merged/finalized usecase diagrams | 30 |
 | Natalia Tabet | UMLLab code generation, <ins>persistence tests for _PhysicalGallery, Customer_ classes</ins>, CrudRepository interfaces for both classes, 3 individual requirements, 3 use cases, 1 activity diagram, 1 individual domain model, model diagrams merging. | 26 |
 
### Sprint 2

The project report for Sprint 2 can be found [here](https://github.com/McGill-ECSE321-Fall2020/project-group-07/wiki/Sprint-2)).

| NAME                   | ROLE | CONTRIBUTIONS | HOURS | 
|------------------------|----------|---------------|-------|
| Anthony Dagher         | Software Developer <br/> Quality Assurance  |**Artist** (4/6 Service methods and unit tests, Controller, Postman) <br/> **Artwork** (2/6 Service methods and unit tests)|   30    |
| Jay Han                | Software Developer <br/> Quality Assurance <br/> C/I Lead, PM  |**Registration** (Service methods, unit tests, Controller, Postman) <br/> **Purchase** (Service methods, unit tests) <br/> **Shipment** (3/6 Sevice methods and unit tests) <br/> Setup travis scripts |   45    | 
| Massimo Vadacchino     | Software Developer <br/> Quality Assurance  |**Admin** (Service methods, unit tests, Controller, Postman) <br/> **Customer** (Service methods, unit tests, Controller, Postman) <br/> **Artist** (2/6 Service methods and unit tests) <br/> **Purchase** (Controller, Postman) <br/> Wiki Project Report|  32  | 
| Keon Olsz              | Software Developer <br/> Quality Assurance  |**Artwork** (4/6 Service methods and unit tests, Controller, Postman)              |   25    |  
| Natalia Tabet          | Software Developer <br/> Quality Assurance  |**Application** (Service methods, unit tests) <br/> **Shipment** (3/6 Service methods and unit tests, DTO, Controller, Postman)              |  30  | 
