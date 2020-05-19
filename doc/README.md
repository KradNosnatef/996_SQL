# Documentation instruction

Please read this instruction before you edit the documentation.

## Prerequisites

You need to install Xelatex if you want to compile latex files on your own computer.

```bash
sudo apt-get install texlive
sudo apt-get install texlive-xetex
sudo apt-get install texlive-lang-chinese
sudo apt-get install texstudio
```

These packages allow you can compile Chinese LaTeX locally.

For Latex package management, you can download any package you want on [CTAN](https://www.ctan.org/pkg/). Or you can simply install full science package:

```bash
sudo apt-get install texlive-full
```

If you do not want to configure latex, you can also visit [Overleaf](https://www.overleaf.com/project) to edit latex online.

## How to generate pdf

After you configure your local latex environment. You can execute:

```bash
make
```

And then you can get a `report.pdf` file on this directory.

You can clean all cache file by using `make cleancache`. If you want to clean all generated files, executing `make cleanall`.

## CAUTION

**WHENEVER YOU PUSH YOUR LOCAL CHANGE, PLEASE CLEANALL GENERATED FILES BEFORE YOU PUSH YOUR BRANCH.**

