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

## Setting Up Commit Hooks

This project uses Git hooks to enforce consistent code quality and commit message standards. Even though this is a Java project, the hooks are powered by Node.js. Follow these steps to set up the hooks locally:

### Prerequisites
- Node.js (v14 or later)
- npm (comes with Node.js)

### Setup Steps

1. **Install Node.js and npm**
   - Download and install from [nodejs.org](https://nodejs.org/)
   - Verify installation with:
     ```
     node --version
     npm --version
     ```

2. **Install dependencies**
   - From the project root directory, run:
     ```
     npm ci
     ```
   - This will install all required dependencies including Husky and commitlint

3. **Verify hooks installation**
   - The hooks should be automatically installed by Husky
   - You can verify by checking if the `.husky` directory contains executable hooks

### Commit Message Convention

This project follows the [Conventional Commits](https://www.conventionalcommits.org/) specification:
- Format: `type(scope): subject`
- Example: `feat(helpline): add call routing feature`

Types include:
- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code changes that neither fix bugs nor add features
- `perf`: Performance improvements
- `test`: Adding or fixing tests
- `build`: Changes to build process or tools
- `ci`: Changes to CI configuration
- `chore`: Other changes (e.g., maintenance tasks, dependencies)

Your commit messages will be automatically validated when you commit, ensuring project consistency.

### Using Commitizen

For an easier commit process, you can use Commitizen:
```
npm run commit
```
This will guide you through creating a properly formatted commit message.

## Usage

All the features have been exposed as REST endpoints. For detailed information on how to use the service, refer to the Swagger API specification.

The Helpline1097 module provides a comprehensive solution for managing various aspects of your application, catering to the needs of individuals and families affected by AIDS.

Feel free to explore the service and make a positive impact on the lives of those seeking support and assistance.

## Filing Issues

If you encounter any issues, bugs, or have feature requests, please file them in the [main AMRIT repository](https://github.com/PSMRI/AMRIT/issues). Centralizing all feedback helps us streamline improvements and address concerns efficiently.  

## Join Our Community

We'd love to have you join our community discussions and get real-time support!  
Join our [Discord server](https://discord.gg/FVQWsf5ENS) to connect with contributors, ask questions, and stay updated.  
