# page-state-machine

Simple Delivery tier Page Flow Control State Machine Library


# Documentation (Local)

The documentation can generated locally by this command:

```bash
$ mvn clean install
$ mvn clean site
```

The output is in the ```target/site/``` directory by default. You can open ```target/site/index.html``` in a browser.

# Documentation (GitHub Pages)

Documentation is available at [https://bloomreach-forge.github.io/page-flow/](https://bloomreach-forge.github.io/page-flow/).

You can generate the GitHub pages only from ```master``` branch by this command:

```bash 
MacOS
$ mvn clean install
$ find docs -name "*.html" -exec rm {} \;
$ mvn -Pgithub.pages clean site

Windows (PowerShell)
$ mvn clean install
$ Get-ChildItem -Path docs -Filter *.html -Recurse | Remove-Item -Force
$ mvn -P"github.pages" clean site
```

The output is in the ```docs/``` directory by default. You can open ```docs/index.html``` in a browser.

You can push it and GitHub Pages will be served for the site automatically.
