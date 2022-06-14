<p align="center"><img src=".github/icon.png" /></p>

# BloodlineSeeker

BloodlineSeeker is a GUI program written in Java, which allows to explore the dynasties of roman emperors. It works by scraping off of Wikipedia the information about the emperors, and then, recursively, scrapes the information of any other person on their bloodline.

![Program screenshot](.github/screenshot.png)

## 🚀 Features

- search every person from every roman dynasty! 🔍
- dynamically explore the searched person's close family graph in the Graph Viewer 🧭
- download the graph of the full dynasty with just 1 click ✨
- search suggestion if the query gives no result ❗
- debug mode from terminal 💻
- dynasties saved locally without having to download them again 🥱

## ❓ How does it work

The starting point of the scraping is [this Wikipedia page](https://it.wikipedia.org/wiki/Imperatori_romani), which lists every roman emperor with the associated Wikipedia links; with these, the program is able to create an object for every emperor on this page, as well as for any other person in their bloodline, recursively. The information about the relatives of any person are taken from the `sinottico` element, which is present in almost every Wikipedia page.

For example, here is the sinottico for Julius Caesar: from this, BloodlineSeeker is able to retrieve the information about his children, his parents and his spouses, and scrape their information recursively.

![Julius Caesar's "sinottico"](.github/sinottico.png)

## ⚠️ Requirements

In order to work properly, BloodlineSeeker needs these external programs to be installed:

- **Java 8+ is required to run the jar file**

- In order to generate the required graphs, the program uses a widespread library called [Graphviz](https://graphviz.org/).
  - On **Windows systems**, all the binaries and DLLs needed are already included.
  - On **UNIX systems**, the Graphviz package should already be installed (due to its popularity). If not, you should be able to download the library with your package manager.

- OPTIONAL: The scraping can be executed either through HTTP Requests, which works out-of-the-box, or by using **Selenium**, which needs one out of the three following browsers to be installed on the system:
  - Microsoft Edge
  - Google Chrome
  - Mozilla Firefox

Support on macOS is not guaranteed, due to the inability to test the program.

## 👷 Installation

The portable executable `BloodlineSeeker.jar` file can be found in the [releases page of this repository](https://github.com/Exyss/BloodlineSeeker/releases). Just download the latest release and you are ready to go.

## 🏎 Run

To run the jar file, simply double-click on it, or run

```sh
java -jar BloodlineSeeker.jar [options]
```

on your terminal. Also, by doing so you can run some other commands that are supported by the program:

- `--help` or `-h` to show the help message
- `--version` or - `-v` to show the version of the program
- `--debug` or `-d` to run the program with the debug mode enabled
- `--no-headless` or `-nh` to run the program without the headless mode enabled (in case the dynasties are downloaded through Selenium)
- `--run-tests` or `-t` to run the tests

## ✔️ Usage

Once `BloodlineSeeker.jar` is run, the user will be prompted with the BloodlineSeeker Launcher on the Load page, which checks if there are any available json files under `data/jsons/` (inside the same folder of the jar file); if any valid json file is found, the program will load it and the launcher will display the option to run the program directly, otherwise the program will show an error message, and the user will be invited to download the dynasties' information by scraping Wikipedia.

In the Download page, the user will be able to choose between HTTP requests and Selenium for loading the HTML pages from Wikipedia, since HTTP requests are *significantly faster*, even when the selenium driver is run with the headless mode activated (which it is by default).

After the download is completed, the files of the found dynasties will be created under `data/jsons/`. Then, the program will launch, giving the user the ability to search through every person of every dynasty, and the option to explore the person's close family graph dynamically in the program.

## 📖 Documentation

To generate the Javadoc, on any UNIX distribution simply run:

```bash
javadoc -classpath "lib/*:lib/graphviz/*:lib/selenium-java-4.1.4/*" -sourcepath ./src **/*.java -d ./doc
```

## ⚙️ Compiling and Contributing

In case you want to compile your own version of this program, you can do it by using the given compile-and-go scripts:

- on Linux, you can use the file named `unix_compile.sh`:

```bash
bash <path_to_repository>/compile.sh [options]
```

- on Windows, you can use the file named `win_compile.bat`:

```bash
<path_to_repository>\win_compile.bat [options]
```

Options on both scripts:

- no options, to compile the program
- `--run` or `-r` to compile and run the program
- `--jar` or `-j` to create an independent and portable jar file
- `--help` or `-h` to show the help message
- `--verbose` or `-v` to run the script with detailed output

To contribute, make sure that the program gets compiled before opening a pull request.
