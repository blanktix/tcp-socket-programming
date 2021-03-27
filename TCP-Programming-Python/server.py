import socket
import threading
HOST_ADDR ="localhost"

def startServer(port):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((HOST_ADDR, port))
    s.listen()
    print("Sedang menjalankan server pada port ", port)
    while True:
        conn, addr = s.accept()
        print("Connected by ", addr, "dari port", port)
        # Membuat thread untuk menangani beberapa client sekaligus dalam satu port
        client_thread =threading.Thread(target=threaded_client, args=(conn, addr,))
        client_thread.start()
def threaded_client(conn, addr):
    with conn:
        req = b""
        while req != b"udah":
            req = conn.recv(1024)
            conn.sendall(b'Kamu mengirimi saya: '+req)
        print("Client {} telah memtutuskan koneksi".format(addr))

if __name__ == "__main__":
    ports = [1234, 2345, 3456, 4567, 5678]
    for port in ports:
        thread =threading.Thread(target=startServer, args=(port, ))
        thread.start()
        # Supaya bisa membuka lima port sekaligus