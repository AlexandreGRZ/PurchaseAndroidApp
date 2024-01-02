package com.example.purchaseclientandroid.Activity.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.purchaseclientandroid.DataBinding.ArticleAdapter;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.R;
import com.example.purchaseclientandroid.networks.*;
import com.example.purchaseclientandroid.networks.RequestObject.CancelRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button Nextbtn;
    private Button Previousbtn;

    private Button Buybtn;

    private Button CancelOnebtn;

    private Button CancelAllbtn;

    private Button ConfirmBtn;

    EditText quantiteTextInput;

    Model model;
    ConsultManager consultManager;

    ImageView imageViewArtcle;

    TextView NameArticleTextView;

    TextView PriceArticleTextView;

    TextView QuantityArticleTexteView;

    ListView ListOfArticleInCaddie;

    ArticleAdapter articleAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        try {
            new ConsultManager(getApplicationContext()).fetchArticleAsync(new ConsultManager.OnArticleFetchListener() {
                @Override
                public void onArticleFetched() {

                    Log.d("articles",model.getDisplayerOfArticle().getArticleToDisplay().toString());
                    displayCurrentArticle();
                }

                @Override
                public void onArticleFetchError(String errorMessage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Consult Fail Try again !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Nextbtn = findViewById(R.id.NextBtn);
        Previousbtn = findViewById(R.id.PreviousBtn);
        Buybtn = findViewById(R.id.btnAcheter);
        CancelOnebtn = findViewById(R.id.btnRetirer);
        CancelAllbtn = findViewById(R.id.btnRetirerTout);
        quantiteTextInput = findViewById(R.id.EditTextQuantity);
        ListOfArticleInCaddie = findViewById(R.id.listViewPanier);
        ConfirmBtn = findViewById(R.id.btnConfirm);

        imageViewArtcle = findViewById(R.id.imageViewArticle);
        NameArticleTextView = findViewById(R.id.textViewArticleName);
        PriceArticleTextView = findViewById(R.id.textViewArticlePrice);
        QuantityArticleTexteView = findViewById(R.id.textViewArticleQuantity);


        try {
            model = Model.getInstance(getApplicationContext());
            ArticleAdapter articleAdapter = new ArticleAdapter(this, new ArrayList<Article>());
            // Associe l'adaptateur à la ListView
            ListOfArticleInCaddie.setAdapter(articleAdapter);


        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.getDisplayerOfArticle().NextArticle();

                try {
                    new ConsultManager(getApplicationContext()).fetchArticleAsync(new ConsultManager.OnArticleFetchListener() {
                        @Override
                        public void onArticleFetched() {

                            Log.d("articles",model.getDisplayerOfArticle().getArticleToDisplay().toString());
                            displayCurrentArticle();
                        }

                        @Override
                        public void onArticleFetchError(String errorMessage) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Consult Fail Try again !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        } );

        Previousbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        model.getDisplayerOfArticle().PreviousArticle();

                        try {
                            new ConsultManager(getApplicationContext()).fetchArticleAsync(new ConsultManager.OnArticleFetchListener() {
                                @Override
                                public void onArticleFetched() {
                                    displayCurrentArticle();
                                    Log.d("articles",model.getDisplayerOfArticle().getArticleToDisplay().toString());
                                }

                                @Override
                                public void onArticleFetchError(String errorMessage) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "Consult Fail Try again !", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        } catch (SQLException | IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
        );

        Buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityToBuy = Integer.parseInt(quantiteTextInput.getText().toString());
                try {
                    new BuyManager(getApplicationContext()).BuyArticleAsync(quantityToBuy, new BuyManager.OnAchatListener() {
                        @Override
                        public void onAchatSuccess() throws SQLException, IOException, ClassNotFoundException {
                            new CaddieManager(getApplicationContext()).CaddieAsync(new CaddieManager.OnCaddieListener() {
                                @Override
                                public void onCaddieSuccess(ArrayList<Article> ListArticleInCaddie) {
                                    Log.d("Caddie", ListArticleInCaddie.toString());
                                    model.setListOfArticleInCaddie(ListArticleInCaddie);
                                    articleAdapter = (ArticleAdapter) ListOfArticleInCaddie.getAdapter();

                                    if (articleAdapter != null) {
                                        articleAdapter.clear();
                                        articleAdapter.addAll(ListArticleInCaddie);
                                        articleAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCaddieError(String errorMessage) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "Caddie Fail Try again !", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }

                        @Override
                        public void onAchatError(String errorMessage) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Buy Fail Try again !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        CancelOnebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Supprimer l'article");
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String articleNameToDelete = input.getText().toString().trim();
                        Article articleToDelete = null;
                        for (Article articleInCaddie: model.getListOfArticleInCaddie()) {
                            if(articleInCaddie.getNom().equals(articleNameToDelete))
                                articleToDelete = articleInCaddie;
                        }

                        if(articleToDelete != null)
                        {
                            try {
                                new CancelManager(getApplicationContext()).CancelAsync(articleToDelete.getId(),new CancelManager.OnCancelFetchListener() {
                                    @Override
                                    public void OnCancelFetch() throws SQLException, IOException, ClassNotFoundException {
                                        new CaddieManager(getApplicationContext()).CaddieAsync(new CaddieManager.OnCaddieListener() {
                                            @Override
                                            public void onCaddieSuccess(ArrayList<Article> ListArticleInCaddie) {
                                                Log.d("Caddie", ListArticleInCaddie.toString());
                                                model.setListOfArticleInCaddie(ListArticleInCaddie);
                                                articleAdapter = (ArticleAdapter) ListOfArticleInCaddie.getAdapter();

                                                if (articleAdapter != null) {
                                                    articleAdapter.clear();
                                                    articleAdapter.addAll(ListArticleInCaddie);
                                                    articleAdapter.notifyDataSetChanged();
                                                }

                                            }

                                            @Override
                                            public void onCaddieError(String errorMessage) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(MainActivity.this, "Caddie Fail Try again !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelFetchError(String errorMessage) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MainActivity.this, "Cancel Fail Try again !", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            } catch (SQLException | IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Aucun article sélectionné.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
        CancelAllbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new CancelAllManager(getApplicationContext()).CancelAllAsync(new CancelAllManager.OnCancelAllFetchListener() {
                        @Override
                        public void OnCancelAllFetch() throws SQLException, IOException, ClassNotFoundException {
                            new CaddieManager(getApplicationContext()).CaddieAsync(new CaddieManager.OnCaddieListener() {
                                @Override
                                public void onCaddieSuccess(ArrayList<Article> ListArticleInCaddie) {
                                    Log.d("Caddie", ListArticleInCaddie.toString());
                                    model.setListOfArticleInCaddie(ListArticleInCaddie);
                                    articleAdapter = (ArticleAdapter) ListOfArticleInCaddie.getAdapter();

                                    if (articleAdapter != null) {
                                        articleAdapter.clear();
                                        articleAdapter.addAll(ListArticleInCaddie);
                                        articleAdapter.notifyDataSetChanged();
                                    }

                                }

                                @Override
                                public void onCaddieError(String errorMessage) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "Caddie Fail Try again !", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }

                        @Override
                        public void onCancelAllFetchError(String errorMessage) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Cancel ALL Fail Try again !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new ConfirmManager(getApplicationContext()).ConfirmAsync(new ConfirmManager.OnConfirmFetchListener() {
                        @Override
                        public void OnConfirmFetch() throws SQLException, IOException, ClassNotFoundException {

                            new CancelAllManager(getApplicationContext()).CancelAllAsync(new CancelAllManager.OnCancelAllFetchListener() {
                                @Override
                                public void OnCancelAllFetch() throws SQLException, IOException, ClassNotFoundException {
                                    new CaddieManager(getApplicationContext()).CaddieAsync(new CaddieManager.OnCaddieListener() {
                                        @Override
                                        public void onCaddieSuccess(ArrayList<Article> ListArticleInCaddie) {
                                            Log.d("Caddie", ListArticleInCaddie.toString());
                                            model.setListOfArticleInCaddie(ListArticleInCaddie);
                                            articleAdapter = (ArticleAdapter) ListOfArticleInCaddie.getAdapter();

                                            if (articleAdapter != null) {
                                                articleAdapter.clear();
                                                articleAdapter.addAll(ListArticleInCaddie);
                                                articleAdapter.notifyDataSetChanged();
                                            }

                                        }

                                        @Override
                                        public void onCaddieError(String errorMessage) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(MainActivity.this, "Caddie Fail Try again !", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });
                                }

                                @Override
                                public void onCancelAllFetchError(String errorMessage) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "Confirm ALL Fail Try again !", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });







                        }

                        @Override
                        public void onConfirmFetchError(String errorMessage) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Confirm Fail Try again !", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void displayCurrentArticle() {
        Article currentArticle = model.getDisplayerOfArticle().getArticleToDisplay();
        Log.d("articles",currentArticle.toString());
        // Met à jour les TextView avec les informations de l'article
        NameArticleTextView.setText(currentArticle.getNom());
        PriceArticleTextView.setText(String.format(Locale.getDefault(), "%.2f €", currentArticle.getPrix()));
        QuantityArticleTexteView.setText(String.valueOf(currentArticle.getQuantite()));

        // Met à jour l'ImageView avec l'image de l'article
        int imageResourceId = getResources().getIdentifier(
                currentArticle.getImg().substring(0, currentArticle.getImg().length() - 4),
                "drawable",
                getPackageName()
        );
        imageViewArtcle.setImageResource(imageResourceId);
    }
}
