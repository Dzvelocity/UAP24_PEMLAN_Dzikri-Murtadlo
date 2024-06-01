import java.util.Map;

public class AksiUser extends Aksi {
    @Override
    public void tampilanAksi() {
        System.out.println("Aksi User:");
        System.out.println("1. Pesan Film");
        System.out.println("2. Lihat Saldo");
        System.out.println("3. Lihat List Film");
        System.out.println("4. Lihat Pesanan");
        System.out.println("5. Logout");
        System.out.println("6. Tutup Aplikasi");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Aplikasi ditutup.");
        System.exit(0);
    }

    @Override
    public void lihatListFilm() {
        Map<String, Film> films = Film.getFilms();
        if (films.isEmpty()) {
            System.out.println("Tidak ada film yang tersedia.");
        } else {
            for (Film film : films.values()) {
                System.out.println(film.getName() + " - " + film.getDescription() + " - Harga: " + film.getPrice() + " - Stok: " + film.getStock());
            }
        }
    }

    public void lihatSaldo() {
        System.out.println("Saldo anda: " + Akun.getCurrentUser().getSaldo());
    }

    public void pesanFilm() {
        Main.scanner.nextLine();
        System.out.print("Nama Film yang ingin dipesan: ");
        String namaFilm = Main.scanner.nextLine();
        Film film = Film.getFilms().get(namaFilm);

        if (film == null) {
            System.out.println("Film yang dicari tidak ditemukan.");
            return;
        }

        System.out.print("Jumlah tiket yang ingin dipesan: ");
        int jumlahTiket = Main.scanner.nextInt();
        double totalHarga = film.getPrice() * jumlahTiket;

        if (jumlahTiket > film.getStock()) {
            System.out.println("Stok tiket tidak mencukupi.");
        } else if (Akun.getCurrentUser().getSaldo() < totalHarga) {
            System.out.println("Saldo tidak mencukupi, saldo yang dimiliki " + Akun.getCurrentUser().getSaldo() + ".");
        } else {
            Akun.getCurrentUser().setSaldo(Akun.getCurrentUser().getSaldo() - totalHarga);
            film.setStock(film.getStock() - jumlahTiket);
            Akun.getCurrentUser().addPesanan(film, jumlahTiket);
            System.out.println("Harga satuan tiket: " + film.getPrice());
            System.out.println("Total harga: " + totalHarga);
            System.out.println("Tiket berhasil dipesan.");
        }
    }


    public void lihatPesanan() {
        Map<String, Pesanan> pesanan = Akun.getCurrentUser().getPesanan();
        if (pesanan.isEmpty()) {
            System.out.println("Kamu belum pernah melakukan pemesanan.");
        } else {
            for (Pesanan p : pesanan.values()) {
                System.out.println("Film: " + p.getFilm().getName() + " - Jumlah: " + p.getKuantitas() + " - Total Harga: " + (p.getFilm().getPrice() * p.getKuantitas()));
            }
        }
    }
}
