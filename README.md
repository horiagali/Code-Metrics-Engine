# Code Quality Checker

## Overview
The Code Quality Checker is a Java application designed to evaluate basic aspects of code quality within a small codebase. It provides functionality to analyze Java files for code complexity and basic code style adherence.

## Features
- **Code Complexity Evaluator**: Analyzes methods/functions for complexity, counting conditional statements.
- **Basic Code Style Check**: Ensures adherence to a specified naming convention for method names.

## Requirements
- Java JDK 8 or higher
- Gradle (optional, for building)

## Usage
1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Run the application.
4. Click on the "Pick Java File" button to select a Java file for analysis.
5. The selected file's content will be displayed in the TextArea.

## How to Contribute
Contributions are welcome! If you'd like to contribute to this project, please follow these steps:
1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/YourFeatureName`).
3. Commit your changes (`git commit -am 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeatureName`).
5. Create a new Pull Request.

## TODO
- fix bug(?) with <init> function
- what if there aren't 3 methods?
- add more code prettiness features
- write tests

## DONE
- create template
- adding code functionality
- parsing code
- finding complexity
- complexities are displayed
- display camelcase percentage

## Low Prioriy
- add translations
- add CSS, make it pretty
- add color to the code (Red where complexity is too high, yellow where conventions aren't followed etc)

  
## License
This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.
