<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/tdiego1/Scheduling_App">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Scheduling App</h3>

  <p align="center">
    The purpose of this application is to add, modify, and delete appointments for 
each customer. This includes various scheduling data that is checked to ensure the data 
is valid, times do not overlap, and are with business hours. Customers can also be added, 
modified, and deleted. Reports can be generated based on the collected data.
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

![Product Name Screen Shot][product-screenshot]

<b>Business Problem</b>: You are working for a software company that has been contracted to develop a GUI-based scheduling desktop 
application. The contract is with a global consulting organization that conducts business in multiple languages and has main 
offices in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. The consulting organization has 
provided a MySQL database that the application must pull data from. The database is used for other systems, so its structure 
cannot be modified.

<p align="right">(<a href="#top">back to top</a>)</p>



### Development Environment
* [IntelliJ Community 2021.1.3](https://www.jetbrains.com/idea/)
* [Java SE 17.0.1](https://www.java.com/en/)
* [JavaFX SDK 17.0.1](https://gluonhq.com/products/javafx/)
* [MySQL](https://www.mysql.com)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

* Install IntelliJ Community 2021.1.3
* Install Java SE 17.0.1
* Install JavaFX SDK 17.0.1

### Installation

1. Download project
2. Open project in IntelliJ
3. Ensure JavaFX is added to project libraries
4. Add JavaFX path variable, `PATH_TO_FX`, in IntelliJ preferences with location of JavaFX
5. Edit run configuration
   1. Modify Options and Add VM options
   2. Add line to VM options
      ```
      --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
      ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Feature 1
- [ ] Feature 2
- [ ] Feature 3
    - [ ] Nested Feature

<!-- See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues). -->

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Diego Torres - tdiego001@gmail.com

Project Link: [https://github.com/tdiego1/Scheduling_App](https://github.com/tdiego1/Scheduling_App)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/main_screen.png
