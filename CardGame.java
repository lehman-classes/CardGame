import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;


class CardGame {

	List<Integer> cards = new ArrayList<>();

	int top = 0;
	int MAX = 51;

	public void shuffle() {

		cards.clear();
		top = 0;
		for (int i = 1; i <= 52; i++) {
			while(true) {
				int r = ThreadLocalRandom.current().nextInt(1, 53);
				if (!cards.contains(r)) {
					cards.add(r);
					break;
				}
			}
		}
	}

	public int drawCard() {
		int c = cards.get(top);
		top++;
		if(top > MAX) {
			top = 0;
		}
		return c;
	}

	public List<Integer> getCards() {
		return cards;
	}

	public static void main(String[] args) {

		JFrame gui = new JFrame();
		CardGame game = new CardGame();

		// program ends when frame closes
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gui.setTitle("Card Game");
		gui.setSize(300,300);

		// center frame
		gui.setLocationRelativeTo(null);

		final Container pane = gui.getContentPane();

		ImageIcon img0 = new ImageIcon("./img/0.png");
		final JLabel card = new JLabel(img0);
		pane.add(card);

		// game.shuffle();
		// System.out.println(game.getCards().toString());

		// buttons
		JButton shuffle = new JButton("Shuffle");
		JButton draw = new JButton("Draw Card");

		shuffle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String command = event.getActionCommand();
				if(command.equals("Shuffle")) {
					game.shuffle();
					System.out.println(game.getCards().toString());
					ImageIcon imgX = new ImageIcon("./img/shuffling.gif");
					card.setIcon(imgX);
					card.updateUI();
					gui.invalidate();
					gui.validate();
					JButton button = (JButton)event.getSource();
					button.setText("Stop");
					draw.setEnabled(false);
				} else {
					card.setIcon(img0);
					card.updateUI();
					gui.invalidate();
					gui.validate();
					JButton button = (JButton)event.getSource();
					button.setText("Shuffle");
					draw.setEnabled(true);
				}
			}
		});
		draw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				int top = game.drawCard();
				ImageIcon img = new ImageIcon("./img/" + top + ".png");
				card.setIcon(img);
				card.updateUI();
				gui.invalidate();
				gui.validate();
			}
		});
		draw.setEnabled(false);
		JPanel buttons = new JPanel();
		buttons.add(shuffle);
		buttons.add(draw);
		pane.add(buttons, BorderLayout.PAGE_END);

		// show frame
		gui.setVisible(true);
	}
}
