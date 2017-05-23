package co.cardseed.cardseed.components.heap;

import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Heap<E> extends Bundle{

    private Object heapObject;

    public Heap() {}

    public Heap(Object obj, boolean extraInfo) {
        super();
        heapObject = obj;
        analizeHeapObjet(extraInfo);
    }

    public Heap(Object obj) {
        super();
        heapObject = obj;
        analizeHeapObjet(false);
    }


    private void analizeHeapObjet(boolean extra) {
        switch (heapObject.getClass().getSimpleName()) {
            case "Cursor":
                this.createCursor(extra);
                break;
            case "ArrayList":
                this.createArrayList();
                break;
            case "JSONArray":
                try {
                    createJSon(heapObject, "array", true);
                } catch (JSONException e) {
                    Log.e("Bad json object", e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "JSONObject":
                try {
                    createJSon(heapObject,"object", true);
                } catch (JSONException e) {
                    Log.e("Bad json object", e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "Bundle":
                createBundle(true);
                break;
            default:
                createObject();
                break;
        }
    }

    private void createCursor(boolean extra) {
        Cursor cursor = (Cursor) heapObject;
        if (extra) {
            String[] colNames = cursor.getColumnNames();
            for (String val : colNames) {
                super.add(val, val);
            }
        } else {
            while (cursor.moveToNext()) {
                for(int i=0; i< cursor.getColumnNames().length; i++){
                    Bundle col = new Bundle();
                    if(cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[i]))!=null){
                        col.add(cursor.getColumnNames()[i],
                                cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[i])));
                    }
                    super.add(col);
                }
            }
        }
    }

    private void createArrayList() {
        ArrayList list = (ArrayList) heapObject;

        for(Object part : list) {
            super.add(part);
        }
    }

    private Bundle createJSon(Object obj, String type, boolean parent) throws JSONException {
        Bundle helper = new Bundle();
        try {
            switch (type) {
                case "array":
                    JSONArray jsonArr = (JSONArray) obj;

                    for (int i = 0; i < jsonArr.length(); i++) {
                        String childType = getJsonType(jsonArr.get(i));
                        if (!childType.equals("none")) {
                            if (parent)
                                super.add(createJSon(jsonArr.get(i), childType, false));
                            else
                                helper.add(createJSon(jsonArr.get(i), childType, false));
                        } else {
                            if (parent)
                                super.add(jsonArr.get(i));
                            else
                                helper.add(jsonArr.get(i));
                        }

                    }
                    break;
                case "object":
                    JSONObject jsonObj = (JSONObject) obj;
                    Iterator keys = jsonObj.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String childType = getJsonType(jsonObj.get(key));
                        if (!childType.equals("none")) {
                            if (parent)
                                super.add(key, createJSon(jsonObj.get(key), childType, false));
                            else
                                helper.add(key, createJSon(jsonObj.get(key), childType, false));
                        } else {
                            if (parent)
                                super.add(key, jsonObj.get(key));
                            else
                                helper.add(key, jsonObj.get(key));
                        }
                    }
                    break;
            }
        } catch (JSONException e) {
            Log.e("JSON Error", e.getMessage());
        }
        return helper;
    }

    private String getJsonType(Object json) {
        switch (json.getClass().getSimpleName()) {
            case "JSONObject":
                return "object";
            case "JSONArray":
                return "array";
            default:
                return "none";
        }
    }

    private void createBundle(Boolean bundle) {
        if (bundle)
            super.createList((Bundle) heapObject);
        else
            super.createList((Bundle) ((Node) heapObject).getValue());
    }

    private void createObject() {
        Node node = (Node) heapObject;
        if (node.getValue() instanceof Bundle)
            createBundle(false);
        else
            super.setPointer((Node) heapObject);
    }

    public Object get(Object index) {
        Object value = null;
        while (super.move()) {
            if (index == super.getIndex()) {
                value =super.getValue();
            } else if(index.equals(super.getIndex())) {
                value = super.getValue();
                break;
            }
        }
        super.restart();
        return value instanceof Bundle ? (Bundle) value : value;
    }

    public Object get() {
        return super.getValue();
    }

    public Object getId() {
        return super.getIndex();
    }

    public void forEach(final Part run) {
        if (super.move()) {
            execute(run);
            forEach(run);
        } else {
            super.restart();
        }
    }

    private void execute(Part run) {
        run.Run(this);
    }

    public boolean isIterable() {
        return this.get() instanceof Bundle;
    }

}
