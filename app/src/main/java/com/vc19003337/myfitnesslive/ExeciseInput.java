package com.vc19003337.myfitnesslive;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExeciseInput extends AppCompatDialogFragment
{
    EditText exerciseTypeET, caloriesBurnedET;
    TextView exerciseTV, caloriesBurnedTV;
    ExeciseInputListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Activity Details")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //Close popup
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String exerciseType = exerciseTypeET.getText().toString();
                        int caloriesBurned = Integer.parseInt(caloriesBurnedET.getText().toString());
                        listener.applyTexts(exerciseType, caloriesBurned);
                    }
                });

        exerciseTypeET = view.findViewById(R.id.et_ExerciseType);
        caloriesBurnedET = view.findViewById(R.id.et_CaloriesBurned);
        exerciseTV = view.findViewById(R.id.tv_Exercise);
        caloriesBurnedTV = view.findViewById(R.id.tv_CaloriesBurned);

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (ExeciseInputListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() +
                    "must implement ExeciseInputListener");
        }
    }

    public interface ExeciseInputListener
    {
        void applyTexts(String exerciseType, int caloriesBurned);
    }
}
