# AMRIT - Helpline1097 Service

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) ![build status](https://github.com/PSMRI/Helpline1097-API/actions/workflows/sast-and-package.yml/badge.svg)

The AMRIT Helpline1097 service aims to provide comprehensive support to individuals and families affected by AIDS, offering a range of services to effectively address their needs.

## Features

- **National Helpline (1097):** The service operates as a national helpline for AIDS-related issues, providing counseling and care to individuals and families affected by AIDS. It supports both inbound and outbound calls.

- **Actors:** The helpline involves two main actors: the counseling officer (CO) and the supervisor. The CO is responsible for providing information, counseling, and referrals to callers seeking help, while the supervisor oversees the helpline's operations.

- **Comprehensive Support:** The helpline offers several services, including:

  - **Information Service:** Providing AIDS-related information such as prevention methods, treatment options, and available support services.
  
  - **Counseling Service:** Offering callers the opportunity to speak with trained professionals who provide emotional support, guidance, and address their concerns.
  
  - **Referral Service:** Connecting callers with relevant healthcare providers, support groups, or organizations that can offer further assistance.
  
  - **Feedback System:** Incorporating a feedback mechanism, allowing callers to provide feedback on their experience with the helpline, helping to improve the quality of support provided.

- **Integration with Everwell System:** The helpline is integrated with the Everwell system. It accesses a list of beneficiaries who have missed medication doses for AIDS treatment. The helpline initiates outbound calls to these beneficiaries, collecting accurate information about missed doses, including medicine type, dosage instructions, phone numbers, and alternate phone numbers. Additionally, it inquires about the reasons behind missed doses. The updated data is then pushed back to the Everwell system, ensuring up-to-date information about beneficiaries and their medication adherence.

## Building from source

To build the Helpline1097 microservice from source, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 1.8
- Maven
- Redis
- Spring Boot v2
- MySQL

### Installation

1. Clone the repository to your local machine.
2. Install the dependencies and build the module:
   - Run `mvn clean install` in the project directory.
3. You can copy `common_example.properties` to `common_local.properties` and edit the file accordingly. The file is under `src/main/environment` folder.
4. Run the development server:
   - Start the Redis server.
   - Run `mvn spring-boot:run` in the project directory.
   - Open your browser and go to `http://localhost:8080/swagger-ui.html#!/` to access the Swagger API documentation.

## Usage

All the features have been exposed as REST endpoints. For detailed information on how to use the service, refer to the Swagger API specification.

The Helpline1097 module provides a comprehensive solution for managing various aspects of your application, catering to the needs of individuals and families affected by AIDS.

Feel free to explore the service and make a positive impact on the lives of those seeking support and assistance.

## Filing Issues

If you encounter any issues, bugs, or have feature requests, please file them in the [main AMRIT repository](https://github.com/PSMRI/AMRIT/issues). Centralizing all feedback helps us streamline improvements and address concerns efficiently.  

## Join Our Community

We’d love to have you join our community discussions and get real-time support!  
Join our [Discord server](https://discord.gg/FVQWsf5ENS) to connect with contributors, ask questions, and stay updated.  
