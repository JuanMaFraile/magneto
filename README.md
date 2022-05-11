#Magneto App

##URLs Pública de invocación

```
(POST) http://magneto-env-1.eba-ugscmtmm.us-east-1.elasticbeanstalk.com/mutant
(GET)  http://magneto-env-1.eba-ugscmtmm.us-east-1.elasticbeanstalk.com/stats
(GET)  http://magneto-env-1.eba-ugscmtmm.us-east-1.elasticbeanstalk.com/health
```


##Ejemplo body para invocar Endpoint POST mutant

```
{
    "dna": [
        "TTGCAAAC",
        "CAGTGCCG",
        "TTATGTCT",
        "AGAAGGCA",
        "TCCCTACG",
        "TCACTGTG",
        "GATCAAAT",
        "GATCAAAT"
    ]
}
```

##Ejemplo URL para invocar Endpoint GET stats localmente

```
http://localhost:8080/stats
```

###Características de la Aplicación
Framework utilizado: Java Micronaut 3.3.2

SDK: AWS Correto 11

##Instrucción para ejecutar

```
gradlew run
```



