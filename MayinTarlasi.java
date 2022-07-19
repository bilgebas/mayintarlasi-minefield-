import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MayinTarlasi implements MouseListener { // interface kullanarak yani implement ederek içindeki sınıfları kullanacağım mouselistener yani tıklanma olaylarını kontrol edeceğim
    JFrame frame;
    Btn[][] board = new Btn[10][10];
    int openButton; //bununla oyunu kazanıp kazanmadığımı anlamak için açılan buton sayısını sayacağım
    public MayinTarlasi() {
        openButton=0; //başlangıç değerine sıfır verdim her açtığımda ++ latacağım
        frame = new JFrame("Mayın Tarlası");
        frame.setSize(800, 800); //jframe boyutu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //jframe açma kapama özelliği
        frame.setLayout(new GridLayout(10, 10)); //10-10 luk ızgara yaptım.
        for (int row = 0; row < board.length; row++) { //satır sayısı (10) kadar row yapsın ve arttırsın
            for (int col = 0; col < board[0].length; col++) { //ilk satırın uzunluğu kadar kolon yapsın arttırsın
                Btn b = new Btn(row, col); //yeni buton ürettim row ve col parametreli
                frame.add(b); //frame in içine bu butonu ekledim
                b.addMouseListener(this);
                board[row][col] = b; //buraya da eklemem gerek tüm işlemleri board üzerinde yapıcam
            }
        }
        generateMine(); //rastgele mayın üretecek
        updateCount(); //etrafta kaç mayın var sayısı
       //print(); // ekrana yazdırmaya yarayacak yazarken kullandım nerde var yok diye

        frame.setVisible(true);
    }

    public void generateMine() { // rastgele mayının üretilip butonun mayın özelliğini true yaptım
        int i=0;
        while(i<10){ // 10 mayın üretmeye karar verdim rastelege row ve col üretip oraya yerleştircem
            int randRow =(int)(Math.random() * board.length); //
            int randCol= (int)(Math.random() * board[0].length); //double bi değer dönüyor inte çevirdik başına yazarak

            while(board[randRow][randCol].isMine()) { //random üretilen yerde mayın var mı yok mu diye önce bakmak için isMine kullandım mayın varsa tekrar rastgele sayı oluşturacak
               randRow = (int) (Math.random() * board.length);
               randCol = (int) (Math.random() * board[0].length);
            }
                board[randRow][randCol].setMine(true); //set değiştirmektir rastgele ürettiğim yerlere daha önce mayın yoksa mayın ekledim
            i++;
        }
        }
        public void print(){
            for (int row = 0; row < board.length; row++)
                for (int col = 0; col < board[0].length; col++){ //bu iki for döngüsü arraydeki tüm elemanları geziyor
                    if(board[row][col].isMine()) { //eğer boardın bu row ve col unda bi mayın varsa oraya icon yani resim ekledim
                        board[row][col].setIcon(new ImageIcon("mine.png"));
                    }else{
                        board[row][col].setText(board[row][col].getCount()+""); //boardın kendi içindeki get count ile değiştir dedim. count int ama set text sadece string alabilir buna +"" yazınca boş bir stringe çeviriyorum. bi sayıyla stringi birleştirince string olur.
                        board[row][col].setEnabled(false); //board umun tüm row col görünümünü açacak mayına tıklanınca
                    }
                }
        }
    public void updateCount(){ //etrafta kaç mayın var sayısı
        for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board[0].length; col++){ //arraydeki tüm satır ve sütunları gezer bu forlar
            if(board[row][col].isMine()){ // eğer orda mayın varsa
                counting(row,col); //bunun etrafındaki 8 li bölgedeki sayıları tek tek gezip değerleri +1 leyeceğiz hangi mayın olduğunun kordinatını da verdim
                    }
                }
            }
        }
        public void counting(int row,int col) { //+1 leyememiz için
            for (int i = row - 1; i <= row + 1; i++) { // row -1 kadar git yani o satırın aşağısına yukarısına çaprazına bakmamızı sağlayan döngü
                for (int k = col - 1; k <= col + 1; k++) { // colun sağına soluna bakmak için de -1 azalttım col +1 e kadar gidecek
                    try{ // en köşelerin row ve col larının -1 leri yok try catch yazmazsak bunun hatasını alırız.
                        int value=board[i][k].getCount();  // i ve j deki count değerini getirecek
                        board[i][k].setCount(++value); //sonra ++value ile değeri +1 değişeceğiz
                    }catch (Exception e){ //bu durumu yakalayınca bana bi hata vermesin bi exception alacağız ama güncellemesi için bir şey vermedim

                    }
                }
            }
        }
    public void open(int r,int c){
      if(r < 0 || r >=board.length || c<0 || c>=board[0].length || board[r][c].getText().length()>0||board[r][c].isEnabled()==false){
          // r 0 dan küçükse yani tıklanması geçersiz yerler alanımın dışında ise returnledim void olduğu için içinde bir şey yok kod bitti
          return;
      }else if(board[r][c].getCount() !=0){ //eger o olumsuzluklar yoksa yani o butonun etrafındaki mayın sayısı 0 dan farklı ise
          board[r][c].setText(board[r][c].getCount()+""); //board umun row ve colunu yazdırdım kapalıydı açtım ya tekrar yazdırmam gerekiyo
          board[r][c].setEnabled(false); //görünürlüğünü açtım
          openButton++; //burda açıldığı için artılattım
      }else{ //0 olma durumu
          openButton++; //
          board[r][c].setEnabled(false); //yine açtım
          open(r-1,c);
          open(r+1,c);
          open(r,c-1);
          open(r,c+1);
      }
    }

    @Override
    public void mouseClicked(MouseEvent e){
        Btn b = (Btn)e.getComponent(); //tıklama olayını bana getirecek tipini btn olarak tanımladık
        if(e.getButton()==1){
            System.out.println("sol tık");
            if(b.isMine()){ //tıkladığın yerdeki butonda mayın varsa
                JOptionPane.showMessageDialog(frame,"Mayına bastınız oyun bitti !");
                print();
            }
            else //değilse
            {
                open(b.getRow(), b.getCol());
                if(openButton==(board.length*board[0].length)-10){ //eğer açılan buton sayımın boardumun totali - 10 kadar açıldıysa oyunu kazanmışım demektir
                    JOptionPane.showMessageDialog(frame,"Tebrikler oyunu kazandınız !");
                    print(); //bunu en sonda mayınları görmek için yazdım
                }}
        }else if(e.getButton() == 3) {
            System.out.println("sağ tık");
            if(!b.isFlag()){ //tıkladığın yerde bayrak yoksa
                b.setIcon(new ImageIcon("flag.png")); //b nin seticonuna yeni bi iconla bayrak ekledim
                b.setFlag(true); //o iconu bayrak yaptım
            }else{
                b.setIcon(null); //yoksa boş bıraktım
                b.setFlag(false); //bayrak yoksa geri false olacak
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Btn button = (Btn) e.getComponent();
        if(button.isMine()){
            board[button.getRow()][button.getCol()].setEnabled(true);
            board[button.getRow()][button.getCol()].setIcon(new ImageIcon("mine.png"));
        }
        else{
            board[button.getRow()][button.getCol()].setEnabled(true);
            board[button.getRow()][button.getCol()].setText(board[button.getRow()][button.getCol()].getCount()+"");

        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Btn button=(Btn) e.getComponent();
        if(button.isMine())
        {
            board[button.getRow()][button.getCol()].setEnabled(true);
            board[button.getRow()][button.getCol()].setIcon(null);
        }
        else{
            board[button.getRow()][button.getCol()].setEnabled(true);
            board[button.getRow()][button.getCol()].setText(null);
        }

    }
}

