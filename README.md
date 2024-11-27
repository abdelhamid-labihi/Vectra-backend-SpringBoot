This project is a Resume Enhancement Service built with Java and Spring Boot. It provides APIs to upload, update, and enhance resumes.

Full Project [here](https://github.com/Vectra-Project)

## Features

- Upload a resume in JSON format.
- Update a resume.
- Get a list of skills from a resume.
- Get a list of missed skills based on a job offer.
- Enhance a resume based on a job offer.

## Classes

- `ResumeService`: This class provides the main business logic for handling resumes. It includes methods to upload, update, and enhance resumes, as well as to get a list of skills from a resume and a list of missed skills based on a job offer.
- `ResumeController`: This class provides the RESTful APIs to interact with the `ResumeService`.
- `JsonFileService`: This class provides methods to write a JSON string to a file.
- `SkillsResponse`: This class represents the response from the `/suggest_skills` endpoint.

## APIs

- `POST /resume/upload`: Upload a resume in JSON format. The resume should be sent in the request body, and the username should be sent as a query parameter.
- `POST /resume/missed_skills`: Get a list of missed skills based on a job offer. The username should be sent as a query parameter, and the job offer details should be sent in the request body.
- `POST /resume/enhance`: Enhance a resume based on a job offer. The username should be sent as a query parameter, and the enhancement details should be sent in the request body.
- `POST /resume/download`: Download the optimized resume in Pdf or Latex format. The username should be sent as a query parameter.
## How to Run

This is a Maven project and can be built and run using the following commands:

```bash
mvn clean install
mvn spring-boot:run
```
## Future Enhancements
- Add authentication and authorization to protect the APIs.
- Add more features to further enhance resumes.
- Improve error handling and add more tests to ensure the quality of the code.

## Final Tests
- Test the APIs using Postman or any other API testing tool.
### Upload API

Endpoint: `/upload`

<details>
  <summary>Data example</summary>

```json
{
    "name": "user_test",
    "email": "user@gmail.com",
    "phone": "+212 600740000",
    "linkedin": "",
    "skills": [
        "HTML",
        "CSS",
        "JavaScript",
        "Bootstrap",
        "Tailwind",
        "Node.js",
        "Express",
        "React.js",
        "Material UI",
        "JWT",
        "Spring Boot",
        "Angular",
        "Java",
        "C",
        "SQL",
        "MongoDB",
        "UML",
        "Merise",
        "Git",
        "GitHub"
    ],
    "experience": [
        {
            "job_title": "Data Analyst Intern",
            "company": "TE Connectivity",
            "description": [
                "Analyzed paper consumption statistics across all departments and processes.",
                "Led development of a Salesforce Tableau dashboard summarizing key results of the analysis.",
                "Identified cost saving opportunities"
            ]
        },
        {
            "job_title": "Software Engineering Intern",
            "company": "1337 Future is Loading",
            "description": [
                "Created a living space co- ownership application for facilitating shared arrangements.",
                "Built using React, Next.js, Prisma ORM, MySQL and Autho."
            ]
        }
    ],
    "education": [
        {
            "degree": "Diplôme d'Études Universitaires Générales",
            "major": "",
            "university": "Ecole Normale de l'enseignement technique ( ENSET )",
            "startDate": "2019-01-01",
            "endDate": "2022-01-01",
            "years": "2019 - 2022"
        }
    ],
    "languages": [
        {
            "language": "ARA",
            "level": "Fluent"
        },
        {
            "language": "FRA",
            "level": "Intermediate"
        },
        {
            "language": "ENG",
            "level": "Intermediate"
        }
    ],
    "softSkills": [
        "Communication",
        "Teamwork"
    ]
}
```
</details>

#### Test using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d @./src/test/test-data/upload.json "http://localhost:8090/resume/upload?username=user_test"
```
#### Response:
`Resume uploaded successfully`
or 
`Error occurred while uploading resume:`

### Missed Skills API

Endpoint: `/missed_skills`

This API is used to get a list of skills that are missing from a resume based on a job offer. The username should be sent as a query parameter, and the job offer details should be sent in the request body.

<details>
  <summary>Data example</summary>

```json
{
  "job_title": "Software Engineer",
  "job_description": "We help large organizations and fast-growing startups secure their systems, their users, and their data.\n\nWe seek a highly motivated and experienced Software Engineer to join us.\n\nThe ideal candidate will have a strong background in programming, good English communication skills, and enjoy working in a fast-paced startup environment.\n\nBy joining us:\n\nYou will\n\nDevelop new services and contribute to existing services to enrich Ostorlab's scanning platform pipelines.\nWork on advanced state-of-the-art static and dynamic security analysis engines\nImplement compelling, usable UIs; contribute to their design;\nContribute to open-source projects\nImprove performance and overcome scalability limits.\nBuild clean, performant, and secure APIs.\nHelp improve our engineering tooling and practices.\nRequirements\n\nExperience working as a Developer.\nYou have experience in an object-oriented programming language (Python, C++, Java Javascript, etc)\nA foundational understanding of data structures, algorithms, and systems\nHigh level of autonomy, can work with a team, and enjoy continuous knowledge and learning from others.\nBonus points\n\nYou have experience contributing to an open-source project.\n\nCareer Benefits\n\nVery competitive compensation package.\nCompetitive Salary\nComprehensive health and dental insurance\nStrong professional development to keep you growing.\nFriendly atmosphere and culture.\nA chance to make an impact",
  "company": "Ostorlab"
}
```
</details>

#### Test using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d @./src/test/test-data/missed_skills "http://localhost:8090/resume/missed_skills?username=user_test"
```
#### Response example:
```json
{
  "technical_skills_present": ["HTML","CSS","JavaScript","Node.js","Express","React.js","Java","C","SQL","MongoDB","Git","GitHub"],
  "technical_skills_missing": ["Python","C++","data structures","algorithms","systems","security analysis","APIs"],
  "soft_skills_present": ["Communication","Teamwork"],
  "soft_skills_missing": ["English communication skills","autonomy","continuous knowledge and learning"],
  "match": ["68.75"]
}
```
### Enhance API

Endpoint: `/enhance`

This API is used to enhance a resume based on a job offer. The username should be sent as a query parameter, and the enhancement details should be sent in the request body.

<details>
  <summary>Data example</summary>

```json
{
  "soft_skills": ["soft_skill_1", "soft_skill_2"],
  "hard_skills": ["hard_skill_1", "hard_skill_2"],
  "job_title": "job_Test",
  "company": "company_test",
  "enhance": true
}
``` 
</details>

#### Test using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d @./src/test/test-data/enhance "http://localhost:8090/resume/enhance?username=user_test"
```
#### Response:
`Resume enhanced successfully`
or
`Error occurred while enhancing resume:`

### Download API

Endpoint: `/download`

This API is used to download a resume in a specified format. The username should be sent as a query parameter, and the job title, company name, and desired format should be sent in the request body.

<details>
  <summary>Data example</summary>

```json
{
    "job_title": "job_Test",
    "company": "company_test",
    "format": "pdf"
}
```
</details>

#### Test using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d @./src/test/test-data/download.json "http://localhost:8090/resume/download?username=user_test" --output resume.pdf
```
