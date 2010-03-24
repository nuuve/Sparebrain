Sparebrain.jar:
	javac excepciones/*.java interfaz/*.java listas/*.java tareas/*.java util/*.java
	jar cvmf manifest Sparebrain.jar excepciones/*.class interfaz/*.class listas/*.class recursos/* tareas/*.class util/*.class
	
clean:
	rm Sparebrain.jar
	rm -r excepciones/*.class interfaz/*.class listas/*.class tareas/*.class util/*.class
	