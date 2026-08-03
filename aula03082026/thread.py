import threading

def tarefa2():
    print("Thread2 executando!")

def tarefa1():
    print("Thread1 executando!")

t1 = threading.Thread(target=tarefa1)
t2 = threading.Thread(target=tarefa2)
t1.start()
t2.start()
