package com.example.home.weddingapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.ImageView;

import com.example.home.weddingapp.Activity.MainActivity;
import com.example.home.weddingapp.R;
import com.example.home.weddingapp.Others.MyLinearLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PadrinhosFragment.PadrinhosFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PadrinhosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PadrinhosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PadrinhosFragmentInteractionListener mListener;

    public PadrinhosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PadrinhosFragment.
     */
    // TODO: Rename and change types and number of parameters
    /**public static PadrinhosFragment newInstance(String param1, String param2) {
        PadrinhosFragment fragment = new PadrinhosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }**/

    public static Fragment newInstance(MainActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, PadrinhosFragment.class.getName(), b);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout) inflater.inflate(R.layout.fragment_padrinhos, container, false);

        int pos = this.getArguments().getInt("pos");
        TextView tv = (TextView) l.findViewById(R.id.text);
        tv.setMovementMethod(new ScrollingMovementMethod());
        ImageView image = (ImageView) l.findViewById(R.id.content);
        switch(pos) {
            case 0:
                tv.setText("Livia & Clebinho. Somos fãs desse casal atleta e guardamos um lugar especial no nosso coração para esses dois. Queremos só e somente só o melhor para esses dois. Deus os abençoe!");
                image.setImageResource(R.drawable.pfoto1);
                break;
            case 1:
                tv.setText("Betinha. Nossa futura advogada, uma menina de fibra, guerreira que é nossa inspiração. Nosso amor por ela não tem medida. É uma alegria tê-la como irmã.");
                image.setImageResource(R.drawable.pfoto2);
                break;
            case 2:
                tv.setText("Juliana & Murillo. São os mais novos pais de família. A chegada de Maya trará muita alegria e nos dará mais um motivo para comemorar. Deus os abençoe e parabéns pela filhota!");
                image.setImageResource(R.drawable.pfoto3);
                break;
            case 3:
                tv.setText("Miguel. O ex-caçula cresceu, e mantendo um coração puro continua sendo o nosso amado Gui. É um menino bom que merece toda a felicidade do mundo. Que Deus o abençoe e ilumine.");
                image.setImageResource(R.drawable.pfoto4);
                break;
            case 4:
                tv.setText("Dudinha. Ela tem um jeitinho doce de demonstrar carinho. Suas cartinhas e desenhos são cheios de amor por todos. Amamos essa boneca!");
                image.setImageResource(R.drawable.pfoto5);
                break;
            case 5:
                tv.setText("Matheus & Bruna. Conheço esses dois desde os tempos de escola. Matheus foi o meu primeiro amigo na escola e já faz quase 20 anos que ele é como um irmão para mim. Desejamos toda a feliciade e sucesso do mundo para este casal pelo qual temos tanto carinho. O Canadá nem imagina o presente que vai receber!");
                image.setImageResource(R.drawable.pfoto6);
                break;
            case 6:
                tv.setText("Tati, Ícaro & Iago. Mais que prima, ela é irmã. Seu amor veio antes do seu nascimento e a inspiração que ela me trouxe fortaleceu meus sonhos. Seu amado Ícaro, além de guardião do coração dela, nos deu nosso pajem lindo, Iago, e nossa cãopanheira diária, Iunna. Nosso amor e gratidão por vocês é imenso.");
                image.setImageResource(R.drawable.pfoto7);
                break;
            case 7:
                tv.setText("Artur. Conheci Artur na segunda série, enquanto esperávamos nossas mães virem nos buscar na escola. Eu, ele e Matheus formavamos um trio inseparável quando éramos pequenos. Sou muito grato por esse irmão que Deus me deu.");
                image.setImageResource(R.drawable.pfoto8);
                break;
            case 8:
                tv.setText("Elaine e Gefferson. Amiga-irmã do coração, verdadeiro presente de Deus, e Geff, que é o dono do valioso coração dela. Não podia pedir por uma amiga melhor, pois não importa o que aconteça, sempre nos entenderemos e é isso que torna uma amizade especial");
                image.setImageResource(R.drawable.pfoto9);
                break;
            case 9:
                tv.setText("Ludmilla. Amiga de todas as horas que se faz presente mesmo a distância. Uma menina de ouro à quem só queremos bem.");
                image.setImageResource(R.drawable.pfoto10);
                break;
            case 10:
                tv.setText("Iunna. Nossa cachorrinha, um dos presentes que Deus nos deu. Thaís fez uma oração e Ele enviou Iunna por express delivery e hoje ela é como uma filha pra nós.");
                image.setImageResource(R.drawable.pfoto11);
                break;
        }

        MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return l;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPadrinhosFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PadrinhosFragmentInteractionListener) {
            mListener = (PadrinhosFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface PadrinhosFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPadrinhosFragmentInteraction(Uri uri);
    }
}
