package il.ac.tau.cs.sw1.trivia;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TriviaGUI {

	private static final int MAX_ERRORS = 3;
	private Shell shell;
	private Label scoreLabel;
	private Composite questionPanel;
	private Label startupMessageLabel;
	private Font boldFont;
	private String lastAnswer;
	private List<Question> questionsList;
	private Integer score;
	private Integer questionsAnswered;
	private Integer wrongAnswersInRowCounter;
	private Boolean gameOver;
	private Boolean usedPassWheelForFirstTime;
	private Boolean usedPassWheelInTurn;
	private Integer numPassWheelUsage;
	private Boolean usedFiftyFiftyWheelForFirstTime;
	private Boolean usedFiftyFiftyWheelInTurn;
	private Integer numFiftyFiftyWheelUsage;
	private Integer currentQuestionIndex;
	private Question currentQuestion;
	private List<String> currentAnswers;
	Label instructionLabel;
	Label questionLabel;
	private List<Button> answerButtons = new LinkedList();
	private Button passButton;
	private Button fiftyFiftyButton;

	public void open() {
		createShell();
		runApplication();
	}

	/**
	 * Creates the widgets of the application main window
	 */
	private void createShell() {
		Display display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Trivia");

		// window style
		Rectangle monitor_bounds = shell.getMonitor().getBounds();
		shell.setSize(new Point(monitor_bounds.width / 3,
				monitor_bounds.height / 4));
		shell.setLayout(new GridLayout());

		FontData fontData = new FontData();
		fontData.setStyle(SWT.BOLD);
		boldFont = new Font(shell.getDisplay(), fontData);

		// create window panels
		createFileLoadingPanel();
		createScorePanel();
		createQuestionPanel();
	}

	/**
	 * Creates the widgets of the form for trivia file selection
	 */
	private void createFileLoadingPanel() {
		final Composite fileSelection = new Composite(shell, SWT.NULL);
		fileSelection.setLayoutData(GUIUtils.createFillGridData(1));
		fileSelection.setLayout(new GridLayout(4, false));

		final Label label = new Label(fileSelection, SWT.NONE);
		label.setText("Enter trivia file path: ");

		// text field to enter the file path
		final Text filePathField = new Text(fileSelection, SWT.SINGLE
				| SWT.BORDER);
		filePathField.setLayoutData(GUIUtils.createFillGridData(1));



		// "Browse" button
		final Button browseButton = new Button(fileSelection, SWT.PUSH);

		browseButton.setText("Browse");


		browseButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String filePathString = GUIUtils.getFilePathFromFileDialog(TriviaGUI.this.shell);
				if (filePathString != null)
					filePathField.setText(filePathString);
			}});





		// todo "Play!" button
		final Button playButton = new Button(fileSelection, SWT.PUSH);
		playButton.setText("Play!");


		playButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = filePathField.getText();


				TriviaGUI.this.resetData();
				TriviaGUI.this.scoreLabel.setText(TriviaGUI.this.score.toString());

				try {
					TriviaGUI.this.questionsList = Question.TextToQuestionArray(path);
					TriviaGUI.this.setNewQuestion();
				} catch (FileNotFoundException fileNotFoundException) {
					GUIUtils.showErrorDialog(TriviaGUI.this.shell, "File " + path + " not found");
				} catch (BadQuestionLineException badQuestionLineException) {
					GUIUtils.showErrorDialog(TriviaGUI.this.shell, String.valueOf(badQuestionLineException.lineNumber));
				}


			}});

	}

	private void updateQuestionPanel(String question, List<String> answers) {
		this.currentAnswers = answers;
		Control[] children = this.questionPanel.getChildren();
		Control[] children1 = children;
		int childrenLength = children.length;

		for(int i = 0; i < childrenLength; ++i) {
			Control control = children1[i];
			control.dispose();
		}

		this.instructionLabel = new Label(this.questionPanel, 16777280);
		this.instructionLabel.setText(this.lastAnswer + "Answer the following question:");
		this.instructionLabel.setLayoutData(GUIUtils.createFillGridData(2));
		this.questionLabel = new Label(this.questionPanel, 16777280);
		this.questionLabel.setText(question);
		this.questionLabel.setFont(this.boldFont);
		this.questionLabel.setLayoutData(GUIUtils.createFillGridData(2));
		this.answerButtons.clear();

		for(int i = 0; i < 4; ++i) {
			Button answerButton = new Button(this.questionPanel, 72);
			answerButton.setText((String)answers.get(i));
			GridData answerLayoutData = GUIUtils.createFillGridData(1);
			answerLayoutData.verticalAlignment = 4;
			answerButton.setLayoutData(answerLayoutData);
			answerButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent arg0) {
					if (!TriviaGUI.this.gameOver) {
						TriviaGUI triviaGUI = TriviaGUI.this;
						triviaGUI.questionsAnswered = triviaGUI.questionsAnswered + 1;
						String answerChosen = ((Button)arg0.getSource()).getText();
						if (TriviaGUI.this.currentQuestion.isRightAnswer(answerChosen)) {
							triviaGUI = TriviaGUI.this;
							triviaGUI.score = triviaGUI.score + 3;
							TriviaGUI.this.wrongAnswersInRowCounter = 0;
							TriviaGUI.this.lastAnswer = "Correct! ";
						} else {
							triviaGUI = TriviaGUI.this;
							triviaGUI.score = triviaGUI.score - 2;
							triviaGUI = TriviaGUI.this;
							triviaGUI.wrongAnswersInRowCounter = triviaGUI.wrongAnswersInRowCounter + 1;
							TriviaGUI.this.lastAnswer = "Wrong... ";
						}

						TriviaGUI.this.setNewQuestion();
					}
				}
			});
			this.answerButtons.add(answerButton);
		}

		this.passButton = new Button(this.questionPanel, 8);
		this.passButton.setText("Pass");
		GridData data = new GridData(3, 2, true, false);
		data.horizontalSpan = 1;
		this.passButton.setLayoutData(data);
		this.passButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (!TriviaGUI.this.usedPassWheelForFirstTime) {
					TriviaGUI.this.usedPassWheelForFirstTime = true;
				}

				TriviaGUI.this.usedPassWheelInTurn = true;
				TriviaGUI triviaGUI = TriviaGUI.this;
				triviaGUI.numPassWheelUsage = triviaGUI.numPassWheelUsage + 1;
				TriviaGUI.this.lastAnswer = "";
				TriviaGUI.this.setNewQuestion();
			}
		});
		this.fiftyFiftyButton = new Button(this.questionPanel, 8);
		this.fiftyFiftyButton.setText("50-50");
		data = new GridData(1, 2, true, false);
		data.horizontalSpan = 1;
		this.fiftyFiftyButton.setLayoutData(data);
		this.fiftyFiftyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (!TriviaGUI.this.usedFiftyFiftyWheelForFirstTime) {
					TriviaGUI.this.usedFiftyFiftyWheelForFirstTime = true;
				}

				TriviaGUI.this.usedFiftyFiftyWheelInTurn = true;
				TriviaGUI triviaGUI = TriviaGUI.this;
				triviaGUI.numFiftyFiftyWheelUsage = triviaGUI.numFiftyFiftyWheelUsage + 1;
				List<Integer> wrongAnswerIndices = new ArrayList();

				for(int i = 0; i < TriviaGUI.this.currentAnswers.size(); ++i) {
					if (!TriviaGUI.this.currentQuestion.isRightAnswer((String)TriviaGUI.this.currentAnswers.get(i))) {
						wrongAnswerIndices.add(i);
					}
				}

				Collections.shuffle(wrongAnswerIndices);
				((Button)TriviaGUI.this.answerButtons.get((Integer)wrongAnswerIndices.get(0))).setEnabled(false);
				((Button)TriviaGUI.this.answerButtons.get((Integer)wrongAnswerIndices.get(1))).setEnabled(false);
				TriviaGUI.this.fiftyFiftyButton.setEnabled(false);
			}
		});
		this.questionPanel.pack();
		this.questionPanel.getParent().layout();
	}






	private void setNewQuestion() {
		if (this.usedPassWheelInTurn && this.numPassWheelUsage > 1) {
			this.score = this.score - 1;
		}

		if (this.usedFiftyFiftyWheelInTurn && this.numFiftyFiftyWheelUsage > 1) {
			this.score = this.score - 1;
		}

		this.usedPassWheelInTurn = false;
		this.usedFiftyFiftyWheelInTurn = false;
		this.scoreLabel.setText(this.score.toString());
		if (this.currentQuestionIndex == this.questionsList.size()) {
			this.gameOver();
		} else if (this.wrongAnswersInRowCounter == 3) {
			this.gameOver();
		} else {
			this.currentQuestion = (Question)this.questionsList.get(this.currentQuestionIndex);
			this.updateQuestionPanel(this.currentQuestion.getTitle(), this.currentQuestion.getAnswersList());
			this.currentQuestionIndex = this.currentQuestionIndex + 1;
			if (this.usedPassWheelForFirstTime && this.score <= 0) {
				this.passButton.setEnabled(false);
			}

			if (this.usedFiftyFiftyWheelForFirstTime && this.score <= 0) {
				this.fiftyFiftyButton.setEnabled(false);
			}

		}
	}

	private void resetData() {
		this.lastAnswer = "";
		this.questionsList = new ArrayList();
		this.score = 0;
		this.currentQuestionIndex = 0;
		this.questionsAnswered = 0;
		this.usedPassWheelInTurn = false;
		this.numPassWheelUsage = 0;
		this.wrongAnswersInRowCounter = 0;
		this.gameOver = false;
		this.usedPassWheelForFirstTime = false;
		this.usedFiftyFiftyWheelForFirstTime = false;
		this.usedFiftyFiftyWheelInTurn = false;
		this.numFiftyFiftyWheelUsage = 0;
	}

	public void gameOver() {
		GUIUtils.showInfoDialog(this.shell, "GAME OVER", "Your final score is " + this.score + " after " + this.questionsAnswered + " questions.");
		this.gameOver = true;
	}


	/**
	 * Creates the panel that displays the current score
	 */
	private void createScorePanel() {
		Composite scorePanel = new Composite(shell, SWT.BORDER);
		scorePanel.setLayoutData(GUIUtils.createFillGridData(1));
		scorePanel.setLayout(new GridLayout(2, false));

		final Label label = new Label(scorePanel, SWT.NONE);
		label.setText("Total score: ");

		// The label which displays the score; initially empty
		scoreLabel = new Label(scorePanel, SWT.NONE);
		scoreLabel.setLayoutData(GUIUtils.createFillGridData(1));
	}

	/**
	 * Creates the panel that displays the questions, as soon as the game
	 * starts. See the updateQuestionPanel for creating the question and answer
	 * buttons
	 */
	private void createQuestionPanel() {
		questionPanel = new Composite(shell, SWT.BORDER);
		questionPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
				true, true));
		questionPanel.setLayout(new GridLayout(2, true));

		// Initially, only displays a message
		startupMessageLabel = new Label(questionPanel, SWT.NONE);
		startupMessageLabel.setText("No question to display, yet.");
		startupMessageLabel.setLayoutData(GUIUtils.createFillGridData(2));
	}


	/**
	 * Opens the main window and executes the event loop of the application
	 */
	private void runApplication() {
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		boldFont.dispose();
	}
}


