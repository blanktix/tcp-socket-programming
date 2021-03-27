import socket

HOST_ADDR="localhost"
PORTS='''
Pilihan port yang terbuka:
    1234
    2345
    3456
    4567
    5678
'''
print(PORTS)
PORT=int(input("Ketikkan port: "))
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST_ADDR, PORT))
    req = ""
    while req!="udah":
        req=input("Masukkan pesan: ")
        s.sendall(bytes(req.encode()))    
        res = s.recv(1024)
        print("Received", repr(res))
    print("Kamu memutuskan koneksi ke server")