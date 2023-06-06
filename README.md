[![License](http://img.shields.io/:license-mit-blue.svg)](https://github.com/Gonzih/cljs-electron/blob/master/LICENSE.md)
![Build](https://github.com/Gonzih/cljs-electron/workflows/CI/badge.svg)

# Clojurified Electron

![](https://raw.githubusercontent.com/Gonzih/cljs-electron/master/demo.gif)

My attempt to recreate ClojureScript development workflow while developing desktop apps with [electron](http://electron.atom.io/).

## What is currently included

* ClojureScript (init script and ui code)
* Figwheel for interactive development
* Reagent for UI
* Advanced compilation with externs inference in release compilation targets

## Running it

```shell
npm install electron -g # install electron binaries
```

### Terminal
```shell
lein cooper # compile cljs and start figwheel
electron .  # start electron from another terminal
```

### Emacs REPL
```shell
lein cljsbuild once
```

<kbd>M-x cider-jack-in-cljs</kbd>
<kbd>figwheel</kbd>

```shell
electron .
```

## Releasing

```shell
lein do clean, cljsbuild once frontend-release, cljsbuild once electron-release
electron . # start electron to test that everything works
```

After that you can follow [distribution guide for the electron.](https://github.com/atom/electron/blob/master/docs/tutorial/application-distribution.md)

The easiest way to package an electron app is by using [electron-packager](https://github.com/maxogden/electron-packager):

```shell
npm install electron-packager -g                                            # install electron packager
electron-packager . HelloWorld --platform=darwin --arch=x64 --electron-version=1.4.8 # package it!
```

## TODO

- Convert to Clojure Deps project
- Integrate Speclj
- Add CSS
- Restructure directories to use project names
- Integrate Scaffold (if possible with two cljs builds)
