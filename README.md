# pinlog
Simple tool that 'pins' the output of tools like nodemon to always start at the top of the terminal window so that 
I eliminate the distraction of the constantly moving text in the console. Has hardcoded rules for nodemon and lein-cljsbuild. 

To use just do

```
nodemon | pinlog
```

Works by clearing the screen when certain pattern is detected in the output.

Rules are loaded from ./.pinlog and must be array with JavaScript futctions.

Example:
```
[
	function lein(str){
		return /Compiling \"/.test(str);
	}
]
```

If you want to run it locally from soucre use ```lein npm link```


