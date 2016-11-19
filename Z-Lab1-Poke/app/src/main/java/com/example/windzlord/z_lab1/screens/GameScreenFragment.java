package com.example.windzlord.z_lab1.screens;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.windzlord.z_lab1.objects.FragmentEvent;
import com.example.windzlord.z_lab1.objects.Pokemon;
import com.example.windzlord.z_lab1.R;
import com.example.windzlord.z_lab1.controllers.Gogo;
import com.example.windzlord.z_lab1.managers.SQLiteContext;
import com.example.windzlord.z_lab1.managers.Preference;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameScreenFragment extends Fragment {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.textView_game_currentScore)
    TextView textViewGameCurrentScore;

    @BindView(R.id.textView_answerA)
    TextView textViewAnswerA;

    @BindView(R.id.textView_answerB)
    TextView textViewAnswerB;

    @BindView(R.id.textView_answerC)
    TextView textViewAnswerC;

    @BindView(R.id.textView_answerD)
    TextView textViewAnswerD;

    @BindView(R.id.view_choiceA)
    View viewChoiceA;

    @BindView(R.id.view_choiceB)
    View viewChoiceB;

    @BindView(R.id.view_choiceC)
    View viewChoiceC;

    @BindView(R.id.view_choiceD)
    View viewChoiceD;

    @BindView(R.id.layout_game_background)
    FrameLayout layoutGameBackground;

    @BindView(R.id.imageView_poke)
    ImageView imageViewPokemon;

    @BindView(R.id.textView_poke)
    TextView textViewPokemon;

    private CountDownTimer countDownTimer;
    private final int MAX = 2000;
    private long remaining;

    private ArrayList<Pokemon> pokemons;
    private Pokemon pokemon;
    private Bitmap bitmapUncensored;
    private int answer;
    private int choose;
    private int score;
    private boolean onGoing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the custom_toast_xml for this fragment
        View view = inflater.inflate(R.layout.fragment_game_screen, container, false);
        settingThingsUp(view);
        goGame();

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        getContent();
    }

    private void getContent() {
        Gogo.goSetFontTextView(getActivity(), textViewGameCurrentScore, "fonts/StencilStd.ttf");
        progressBar.setMax(MAX);
        remaining = MAX;
        score = 0;
        textViewGameCurrentScore.setText(score + "");
    }

    private void goGame() {
        ArrayList<Integer> genes = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            if (Preference.getInstance().getStatusGene(i)) genes.add(i);
        }
        pokemons = SQLiteContext.getInstance().getPokemons(genes);

        nextQuestion();
    }

    private void nextQuestion() {
        goScreen();
        goHint();
        goChoices();
    }

    private void goScreen() {
        onGoing = true;

        countDownTimer = new CountDownTimer(remaining + 1, 1) {

            public void onTick(long millisUntilFinished) {
                long progress = MAX - millisUntilFinished;
                progressBar.setProgress((int) progress);
                remaining = millisUntilFinished;
            }

            public void onFinish() {
                EventBus.getDefault().post("Game Over");
                onStop();
            }
        }.start();

        pokemon = pokemons.get(Gogo.getRandom(pokemons.size()));

        Gogo.goSetBackgroundView(getActivity(), viewChoiceA, R.drawable.circle_background);
        Gogo.goSetBackgroundView(getActivity(), viewChoiceB, R.drawable.circle_background);
        Gogo.goSetBackgroundView(getActivity(), viewChoiceC, R.drawable.circle_background);
        Gogo.goSetBackgroundView(getActivity(), viewChoiceD, R.drawable.circle_background);
    }

    private void goHint() {
        bitmapUncensored = Gogo.getBitmap(getActivity(), "images/" + pokemon.getImage());

        Bitmap bitmapCensored = bitmapUncensored.copy(Bitmap.Config.ARGB_8888, true);
        Gogo.changeColor(bitmapCensored);

        textViewPokemon.setText("");
        imageViewPokemon.setImageBitmap(bitmapCensored);
        layoutGameBackground.setBackgroundColor(Color.parseColor(pokemon.getColor()));
    }

    private void goChoices() {
        int gene = pokemon.getGene();
        ArrayList<Pokemon> pokemons = SQLiteContext.getInstance().getPokemons(gene);
        int target = -1;
        for (Pokemon poke : pokemons) {
            target++;
            if (poke.getId() == pokemon.getId()) break;
        }
        int[] answers = Gogo.getRandoms(pokemons.size(), target);
        answer = Gogo.getRandom(4);
        switch (answer) {
            case 0:
                textViewAnswerA.setText(pokemons.get(target).getName());
                textViewAnswerB.setText(pokemons.get(answers[0]).getName());
                textViewAnswerC.setText(pokemons.get(answers[1]).getName());
                textViewAnswerD.setText(pokemons.get(answers[2]).getName());
                break;
            case 1:
                textViewAnswerA.setText(pokemons.get(answers[0]).getName());
                textViewAnswerB.setText(pokemons.get(target).getName());
                textViewAnswerC.setText(pokemons.get(answers[1]).getName());
                textViewAnswerD.setText(pokemons.get(answers[2]).getName());
                break;
            case 2:
                textViewAnswerA.setText(pokemons.get(answers[0]).getName());
                textViewAnswerB.setText(pokemons.get(answers[1]).getName());
                textViewAnswerC.setText(pokemons.get(target).getName());
                textViewAnswerD.setText(pokemons.get(answers[2]).getName());
                break;
            case 3:
                textViewAnswerA.setText(pokemons.get(answers[0]).getName());
                textViewAnswerB.setText(pokemons.get(answers[1]).getName());
                textViewAnswerC.setText(pokemons.get(answers[2]).getName());
                textViewAnswerD.setText(pokemons.get(target).getName());
                break;
        }
    }

    private void showAnswer() {
        String pokeTago = pokemon.getTag() + " " + pokemon.getName();
        textViewPokemon.setText(pokeTago);
        imageViewPokemon.setImageBitmap(bitmapUncensored);

        switch (answer) {
            case 0:
                Gogo.goSetBackgroundView(getActivity(), viewChoiceA, R.drawable.circle_background_true);
                break;
            case 1:
                Gogo.goSetBackgroundView(getActivity(), viewChoiceB, R.drawable.circle_background_true);
                break;
            case 2:
                Gogo.goSetBackgroundView(getActivity(), viewChoiceC, R.drawable.circle_background_true);
                break;
            case 3:
                Gogo.goSetBackgroundView(getActivity(), viewChoiceD, R.drawable.circle_background_true);
                break;
        }

        if (choose == answer) textViewGameCurrentScore.setText(++score + "");

        onGoing = false;
        countDownTimer.cancel();
    }

    @OnClick(R.id.view_choiceA)
    public void chooseA() {
        if (!onGoing) return;
        choose = 0;
        Gogo.goSetBackgroundView(getActivity(), viewChoiceA, R.drawable.circle_background_false);
        showAnswer();
    }

    @OnClick(R.id.view_choiceB)
    public void chooseB() {
        if (!onGoing) return;
        choose = 1;
        Gogo.goSetBackgroundView(getActivity(), viewChoiceB, R.drawable.circle_background_false);
        showAnswer();
    }

    @OnClick(R.id.view_choiceC)
    public void chooseC() {
        if (!onGoing) return;
        choose = 2;
        Gogo.goSetBackgroundView(getActivity(), viewChoiceC, R.drawable.circle_background_false);
        showAnswer();
    }

    @OnClick(R.id.view_choiceD)
    public void chooseD() {
        if (!onGoing) return;
        choose = 3;
        Gogo.goSetBackgroundView(getActivity(), viewChoiceD, R.drawable.circle_background_false);
        showAnswer();
    }

    @OnClick(R.id.imageView_poke)
    public void go() {
        if (onGoing) return;
        nextQuestion();
    }


    @Override
    public void onStop() {
        super.onStop();
        goGameOver();
    }

    private void goGameOver() {
        countDownTimer.cancel();
        Preference.getInstance().putScore(score);
        int highScore = Preference.getInstance().getHighScore();
        Preference.getInstance().putHighScore(Math.max(highScore, score));
        EventBus.getDefault().post(new FragmentEvent(
                new MainScreenFragment(), false
        ));
    }

}
