# cpulimitController
cpulimit frontend on linux



```
:~# cpulimit --pid=12728  --monitor-forks  --limit=10
Process 12728 detected
//kill process
Process 12728 dead!
:~# 
:~# cpulimit --pid=12728  --monitor-forks  --limit=10
No process found

```


```
~# cpulimit -l 6 -p 6276
Process 6276 detected
Process 6276 dead!
~#  echo $?
2


```

```
~# cpulimit -l 6 -p 81
No process found
~# echo $?
2

```

```
:~# cpulimit -l 6 -p 8162
Process 8162 detected
^CExiting...
:~# echo $?
0
```