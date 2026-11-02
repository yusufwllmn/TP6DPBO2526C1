# Flappy Bird

## Janji

Saya Yusuf Willman Hammam dengan NIM 2511185 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain

Game Flappy Bird berbasis GUI (Java Swing) dengan Main Menu, skor, game over, restart, dan audio.

| Class | Fungsi |
|---|---|
| `App` | Titik masuk program, mengatur perpindahan antara Main Menu dan game |
| `MenuPanel` | Main Menu dengan tombol **Mulai Permainan** dan **Keluar** |
| `View` | Menggambar background, burung, pipa, `JLabel` skor, dan tampilan Game Over |
| `Logic` | Game loop, gerakan, tabrakan, skor, restart, dan pemanggilan audio |
| `Player` | Data burung (posisi, ukuran, gambar, kecepatan) |
| `Pipe` | Data pipa (posisi, ukuran, gambar, status sudah dilewati) |
| `Sound` | Memutar BGM dan sound effect |

## Alur Program

1. Program dijalankan, muncul Main Menu. Klik **Keluar** untuk menutup program, atau **Mulai Permainan** untuk bermain.
2. Tekan **Spasi** agar burung naik. Burung jatuh karena gravitasi.
3. Setiap burung melewati sepasang pipa, skor bertambah 1.
4. Jika burung menabrak pipa atau jatuh ke batas bawah, permainan berhenti dan muncul tulisan **GAME OVER**.
5. Tekan **R** untuk bermain lagi dari awal.

Audio: BGM selama bermain, serta sound effect saat burung naik, melewati pipa, dan kalah.

## Dokumentasi
https://github.com/user-attachments/assets/656a7de1-9bc6-4fd6-9f98-9a3631855cdf
