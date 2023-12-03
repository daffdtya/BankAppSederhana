import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Rekening {
    private String nomorRekening;
    private double saldo;
    private List<String> transaksiList;

    public Rekening(String nomorRekening, double saldoAwal) {
        this.nomorRekening = nomorRekening;
        this.saldo = saldoAwal;
        this.transaksiList = new ArrayList<>();
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void tambahSaldo(double jumlah) {
        saldo += jumlah;
        transaksiList.add("Deposit = " + jumlah);
    }

    public void tarikSaldo(double jumlah) {
        if (jumlah <= saldo) {
            saldo -= jumlah;
            transaksiList.add("Withdrawal = " + jumlah);
        } else {
            System.out.println("Saldo tidak cukup");
        }
    }

    public List<String> getTransaksiList() {
        return transaksiList;
    }
}

class BankAccount extends Rekening {
    public BankAccount(String nomorRekening, double initialBalance) {
        super(nomorRekening, initialBalance);
    }

    public void tambahSaldo(double jumlah) {
        super.tambahSaldo(jumlah);
        System.out.println("Saldo Bank Account ditambahkan. Saldo sekarang: " + getSaldo());
    }
}

class CreditCard extends Rekening {
    private double creditLimit;

    public CreditCard(String nomorRekening, double creditLimit) {
        super(nomorRekening, 0);
        this.creditLimit = creditLimit;
    }

    public void tambahSaldo(double jumlah) {
        if (jumlah <= creditLimit) {
            super.tambahSaldo(jumlah);
            System.out.println("Saldo Credit Card ditambahkan. Saldo sekarang: " + getSaldo());
        } else {
            System.out.println("Credit limit exceeded");
        }
    }
}

class Nasabah {
    private String nama;
    private String alamat;
    private Rekening rekening;

    public Nasabah(String nama, String alamat, Rekening rekening) {
        this.nama = nama;
        this.rekening = rekening;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public Rekening getRekening() {
        return rekening;
    }
}

class Teller {
    private String nama;
    private String password;
    private List<Nasabah> nasabahList;

    public Teller(String nama, String password) {
        this.nama = nama;
        this.password = password;
        this.nasabahList = new ArrayList<>();
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }

    public List<Nasabah> getNasabahList() {
        return nasabahList;
    }

    public void tambahNasabah(Nasabah nasabah) {
        nasabahList.add(nasabah);
    }

    public void cekNasabah(Bank bank) {
        System.out.println("Daftar Nasabah:");
        for (Nasabah nasabah : bank.getNasabahList()) {
            System.out.println("- " + nasabah.getNama());
        }
    }

    public void detailAktivitas(Nasabah nasabah) {
        System.out.println("Detail Aktivitas untuk " + nasabah.getNama() + ":");
        List<String> transaksiList = nasabah.getRekening().getTransaksiList();
        for (String transaksi : transaksiList) {
            System.out.println("- " + nasabah.getNama() + " - " + transaksi);
        }
    }
}

class Bank {
    private List<Nasabah> nasabahList;
    private List<Teller> tellerList;

    public Bank() {
        this.nasabahList = new ArrayList<>();
        this.tellerList = new ArrayList<>();
    }

    public void tambahNasabah(Nasabah nasabah) {
        nasabahList.add(nasabah);
    }

    public void tambahTeller(Teller teller) {
        tellerList.add(teller);
    }

    public List<Nasabah> getNasabahList() {
        return nasabahList;
    }

    public List<Teller> getTellerList() {
        return tellerList;
    }
}

class Loan {
    private double loanAmount;
    private double interestRate;
    private int loanTerm;

    public Loan(double loanAmount, double interestRate, int loanTerm) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public double calculateMonthlyPayment() {
        return 0.0;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Logger {
    public void logTransaction(Nasabah nasabah, Transaction transaction) {
        System.out.println("Logging transaction for " + nasabah.getNama() +
                ": " + transaction.getType() + " of " + transaction.getAmount());
    }

    public void logTransaction(Rekening account, Transaction transaction) {
        System.out.println("Logging transaction for account " + account.getNomorRekening() +
                ": " + transaction.getType() + " of " + transaction.getAmount());
    }
}

public class BankApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Bank bank = new Bank();

        // Menambahkan data default Nasabah dan Teller
        Rekening rekeningDefault = new Rekening("1", 0);
        Nasabah nasabahDefault = new Nasabah("Nasabah", "PENS", rekeningDefault);
        bank.tambahNasabah(nasabahDefault);

        Teller tellerDefault = new Teller("Teller", "1234");
        bank.tambahTeller(tellerDefault);

        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("Selamat datang di Bank App!");
            System.out.print("Masukkan peran (Nasabah/Teller/Exit): ");
            String peran = scanner.nextLine();

            switch (peran.toLowerCase()) {
                case "nasabah":
                    System.out.print("Masukkan nama Nasabah: ");
                    String namaNasabah = scanner.nextLine();
                    Nasabah nasabahLogin = findNasabah(bank, namaNasabah);
                    if (nasabahLogin != null) {
                        System.out.println("Login berhasil sebagai Nasabah.");
                        menuNasabah(scanner, nasabahLogin, bank);
                    } else {
                        System.out.println("Nasabah tidak ditemukan.");
                    }
                    break;
                case "teller":
                    System.out.print("Masukkan nama Teller: ");
                    String namaTeller = scanner.nextLine();
                    Teller tellerLogin = findTeller(bank, namaTeller);
                    if (tellerLogin != null) {
                        System.out.println("Login berhasil sebagai Teller.");
                        menuTeller(scanner, tellerLogin, bank);
                    } else {
                        System.out.println("Teller tidak ditemukan.");
                    }
                    break;
                case "exit":
                    exitProgram = true;
                    System.out.println("Terima kasih! Program berakhir.");
                    break;
                default:
                    System.out.println("Peran tidak valid.");
            }
        }
    }

    private static void menuNasabah(Scanner scanner, Nasabah nasabah, Bank bank) {

        int menuNasabah;
        do {
            System.out.println("\nMenu Nasabah:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");
            System.out.println("3. Detail Transaksi");
            System.out.println("4. Back to Login");
            System.out.print("Pilih menu: ");
            menuNasabah = scanner.nextInt();

            switch (menuNasabah) {
                case 1:
                    System.out.print("Masukkan jumlah deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    nasabah.getRekening().tambahSaldo(depositAmount);
                    System.out.println("Deposit berhasil. Saldo sekarang: " + nasabah.getRekening().getSaldo());
                    break;
                case 2:
                    System.out.print("Masukkan jumlah withdrawal: ");
                    double withdrawalAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    if (withdrawalAmount <= nasabah.getRekening().getSaldo()) {
                        nasabah.getRekening().tarikSaldo(withdrawalAmount);
                        System.out.println("Withdrawal berhasil. Saldo sekarang: " + nasabah.getRekening().getSaldo());
                    } else {
                        System.out.println("Saldo tidak mencukupi");
                    }
                    break;
                case 3:
                    System.out.println("Detail Transaksi:");
                    List<String> transaksiList = nasabah.getRekening().getTransaksiList();
                    for (String transaksi : transaksiList) {
                        System.out.println("- " + transaksi);
                    }
                    break;
                case 4:
                    System.out.println("Terima kasih, " + nasabah.getNama() + "!");
                    bank.tambahNasabah(nasabah); 
                    scanner.nextLine(); 
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (menuNasabah != 4);
    }

    private static void menuTeller(Scanner scanner, Teller teller, Bank bank) {

        int menuTeller;
        do {
            System.out.println("\nMenu Teller:");
            System.out.println("1. Cek Nasabah");
            System.out.println("2. Detail Aktivitas Nasabah");
            System.out.println("3. Back to Login");
            System.out.print("Pilih menu: ");
            menuTeller = Integer.parseInt(scanner.nextLine());

            switch (menuTeller) {
                case 1:
                    teller.cekNasabah(bank);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.print("Masukkan nama Nasabah: ");
                    String namaNasabah = scanner.nextLine();
                    // Mencari Nasabah berdasarkan nama
                    Nasabah nasabah = null;
                    for (Nasabah n : teller.getNasabahList()) {
                        if (n.getNama().equalsIgnoreCase(namaNasabah)) {
                            nasabah = n;
                            break;
                        }
                    }
                    if (nasabah != null) {
                        teller.detailAktivitas(nasabah);
                    } else {
                        System.out.println("Nasabah tidak ditemukan.");
                    }
                    break;
                case 3:
                    System.out.println("Terima kasih!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (menuTeller != 3);
    }

    private static Nasabah findNasabah(Bank bank, String nama) {
        for (Nasabah nasabah : bank.getNasabahList()) {
            if (nasabah.getNama().equalsIgnoreCase(nama)) {
                return nasabah;
            }
        }
        return null;
    }

    private static Teller findTeller(Bank bank, String nama) {
        for (Teller teller : bank.getTellerList()) {
            if (teller.getNama().equalsIgnoreCase(nama)) {
                return teller;
            }
        }
        return null;
    }
}