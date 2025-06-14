import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class MayinTarlasi {
    int satirSayisi;
    int sutunSayisi;
    int alanBoyutu;
    String[][] tahta;
    int[][] mayinlar;
    boolean oyunBitti = false;
    Scanner giris = new Scanner(System.in);

    public MayinTarlasi(int satirSayisi, int sutunSayisi) {
        this.satirSayisi = satirSayisi;
        this.sutunSayisi = sutunSayisi;
        this.alanBoyutu = satirSayisi * sutunSayisi;
        this.tahta = new String[satirSayisi][sutunSayisi];
        this.mayinlar = new int[satirSayisi][sutunSayisi];
    }

    public void oyunuBaslat() {
        System.out.println("🧨 Mayın Tarlası Oyununa Hoşgeldiniz!");
        tahtaOlustur();
        mayinlariYerlestir();
        mayinHaritasiYazdir(); // Geliştirici görünümü

        while (!oyunBitti) {
            tahtaYazdir();
            System.out.print("Satır girin: ");
            int satir = giris.nextInt();
            System.out.print("Sütun girin: ");
            int sutun = giris.nextInt();

            if (satir < 0 || satir >= satirSayisi || sutun < 0 || sutun >= sutunSayisi) {
                System.out.println("❌ Geçersiz koordinat. Lütfen tekrar deneyin.");
                continue;
            }

            if (mayinlar[satir][sutun] == -1) {
                System.out.println("\n💥 Mayına bastınız! Oyun bitti.");
                mayinlariGoster();
                tahtaYazdir();
                oyunBitti = true;
            } else {
                int sayi = etraftakiMayinSayisi(satir, sutun);
                tahta[satir][sutun] = String.valueOf(sayi);

                if (kazandinizMi()) {
                    System.out.println("🎉 Tebrikler, tüm güvenli alanları açtınız!");
                    tahtaYazdir();
                    oyunBitti = true;
                }
            }
        }
    }

    // Tahtayı başlat (- ile doldur)
    private void tahtaOlustur() {
        for (int i = 0; i < satirSayisi; i++) {
            Arrays.fill(tahta[i], "-");
            Arrays.fill(mayinlar[i], 0);
        }
    }

    // Mayınları rastgele yerleştir
    private void mayinlariYerlestir() {
        int mayinSayisi = alanBoyutu / 4;
        Random rastgele = new Random();
        int yerlestirilen = 0;

        while (yerlestirilen < mayinSayisi) {
            int i = rastgele.nextInt(satirSayisi);
            int j = rastgele.nextInt(sutunSayisi);
            if (mayinlar[i][j] != -1) {
                mayinlar[i][j] = -1;
                yerlestirilen++;
            }
        }
    }

    // Geliştirici için mayın konumlarını göster
    private void mayinHaritasiYazdir() {
        System.out.println("\n=== Mayınların Konumu  ===");
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if (mayinlar[i][j] == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    // Oyuncuya gösterilen tahtayı yazdır
    private void tahtaYazdir() {
        System.out.println("\n=== Mayın Tarlası ===");
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                System.out.print(tahta[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Etrafındaki mayın sayısını say
    private int etraftakiMayinSayisi(int x, int y) {
        int sayac = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < satirSayisi && j >= 0 && j < sutunSayisi) {
                    if (i != x || j != y) {
                        if (mayinlar[i][j] == -1) {
                            sayac++;
                        }
                    }
                }
            }
        }
        return sayac;
    }

    // Oyuncu mayına bastığında tüm mayınları göster
    private void mayinlariGoster() {
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if (mayinlar[i][j] == -1) {
                    tahta[i][j] = "*";
                }
            }
        }
    }

    // Oyunu kazanıp kazanmadığını kontrol et
    private boolean kazandinizMi() {
        int acilanHucreSayisi = 0;
        for (int i = 0; i < satirSayisi; i++) {
            for (int j = 0; j < sutunSayisi; j++) {
                if (!tahta[i][j].equals("-")) {
                    acilanHucreSayisi++;
                }
            }
        }
        return acilanHucreSayisi == (alanBoyutu - (alanBoyutu / 4));
    }
}
