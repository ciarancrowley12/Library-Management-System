package library;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class LibrarySystem extends JFrame {

    JPanel memberTab, bookTab, summaryTab;
    ArrayList<Member> memberList = new ArrayList<>();
    ArrayList<BookLoan> loanList = new ArrayList<>();
    int currentMember = -1;

    JTextArea loanArea;
    JTextField memberNameTxt, memberIdTxt;
    JTextField bookCodeTxt, fineTxt;
    JRadioButton referenceRb, regularRb;
    JLabel lbTotalFine;

    public LibrarySystem() {
        super("Library Management System");

        memberTab = new JPanel();
        bookTab = new JPanel();
        summaryTab = new JPanel();

        JTabbedPane tabs = new JTabbedPane();
        tabs.add(memberTab, "Member Information");
        tabs.add(bookTab, "Borrowed Books");
        tabs.add(summaryTab, "Member Summary");

        this.add(tabs);

        MemberGUI();
        BookGUI();
        SummaryGUI();
    }

    public void MemberGUI() {
        memberTab.setLayout(new BorderLayout());

        JLabel lb1 = new JLabel("Member Name:");
        JLabel lb2 = new JLabel("Member ID:");

        memberNameTxt = new JTextField(20);
        memberIdTxt = new JTextField(20);

        JButton addBtn = new JButton("Add Member");
        JButton nextBtn = new JButton("Next Member");

        JPanel inputPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        inputPanel.add(lb1);
        inputPanel.add(memberNameTxt);
        inputPanel.add(lb2);
        inputPanel.add(memberIdTxt);
        inputPanel.add(addBtn);

        buttonPanel.add(nextBtn);

        memberTab.add(inputPanel, BorderLayout.NORTH);
        memberTab.add(buttonPanel, BorderLayout.SOUTH);

    
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Member member = new Member(memberNameTxt.getText(), memberIdTxt.getText());
                memberList.add(member);
                currentMember++;
                showMemberDetails();
                updateSummary();
            }
        });

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMember >= 0) {
                    if (currentMember == memberList.size() - 1)
                        currentMember = 0;
                    else
                        currentMember++;

                    showMemberDetails();
                    updateSummary();
                }
            }
        });
    }

    public void BookGUI() {
        JPanel inputPanel = new JPanel();
        JPanel displayPanel = new JPanel();

        JLabel lb1 = new JLabel("Book Code:");
        JLabel lb2 = new JLabel("Fine Amount:");

        bookCodeTxt = new JTextField(15);
        fineTxt = new JTextField(10);

        referenceRb = new JRadioButton("Reference Book");
        regularRb = new JRadioButton("Regular Book", true);

        ButtonGroup group = new ButtonGroup();
        group.add(referenceRb);
        group.add(regularRb);

        JButton addBtn = new JButton("Add Book Loan");

        loanArea = new JTextArea(10, 40);

        inputPanel.add(lb1);
        inputPanel.add(bookCodeTxt);
        inputPanel.add(lb2);
        inputPanel.add(fineTxt);
        inputPanel.add(regularRb);
        inputPanel.add(referenceRb);
        inputPanel.add(addBtn);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = bookCodeTxt.getText();
                if (!code.isEmpty()) {
                    try {
                        double fine = Double.parseDouble(fineTxt.getText());
                        boolean reference = referenceRb.isSelected();

                        BookLoan loan = new BookLoan(code, fine, reference);
                        loanList.add(loan);
                        loanArea.append(loan.toString() + "\n");

                        if (currentMember != -1) {
                            memberList.get(currentMember).borrowBook(loan);
                            bookCodeTxt.setText("");
                            fineTxt.setText("");
                        }

                        updateSummary();

                    } catch (Exception ex) {
                        System.out.println("Invalid input");
                    }
                }
            }
        });

        displayPanel.add(new JScrollPane(loanArea));

        bookTab.setLayout(new BorderLayout());
        bookTab.add(inputPanel, BorderLayout.NORTH);
        bookTab.add(displayPanel, BorderLayout.CENTER);
    }

    public void SummaryGUI() {
        lbTotalFine = new JLabel("Total fine for member: 0");
        summaryTab.add(lbTotalFine);
    }

    public void showMemberDetails() {
        if (currentMember != -1) {
            Member m = memberList.get(currentMember);
            memberNameTxt.setText(m.getName());
            memberIdTxt.setText(m.getMemberId());

            loanArea.setText("");
            loanArea.setText(m.getBorrowedBooksString());

            bookCodeTxt.setText("");
            fineTxt.setText("");
        }
    }

    public void updateSummary() {
        if (currentMember != -1 && !memberNameTxt.getText().isEmpty()) {
            double totalFine = memberList.get(currentMember).getTotalFine();
            lbTotalFine.setText(
                "Total fine for " + memberNameTxt.getText() + ": " + totalFine
            );
        }
    }

    public static void main(String[] args) {
        LibrarySystem win = new LibrarySystem();
        win.setVisible(true);
        win.pack();
    }
}
