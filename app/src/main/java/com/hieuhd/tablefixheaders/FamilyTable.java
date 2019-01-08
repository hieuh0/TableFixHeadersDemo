package com.hieuhd.tablefixheaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hieuhd.mylibrary.TableFixHeaders;
import com.hieuhd.mylibrary.adapters.BaseTableAdapter;

import java.util.ArrayList;
import java.util.List;

public class FamilyTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
        BaseTableAdapter baseTableAdapter = new FamilyNexusAdapter(this);
        tableFixHeaders.setAdapter(baseTableAdapter);
    }
    private class NexusTypes {
        private final String name;
        private final List<Nexus> list;

        NexusTypes(String name) {
            this.name = name;
            list = new ArrayList<Nexus>();
        }

        public int size() {
            return list.size();
        }

        public Nexus get(int i) {
            return list.get(i);
        }
    }

    private class Nexus {
        private final String[] data;

        private Nexus(String name, String Toan, String Van, String Tin, String Su, String Dia, String Hoa,String td) {
            data = new String[] {
                    name,
                    Toan,
                    Van,
                    Tin,
                    Su,
                    Dia,
                    Hoa,td };
        }
    }
    public class FamilyNexusAdapter extends BaseTableAdapter {

        private final NexusTypes familys[];
        private final String headers[] = {
                "Toan",
                "Van",
                "Tin",
                "Su",
                "Dia",
                "Hoa",
                "The Duc",
        };

        private final int[] widths = {
                120,
                100,
                140,
                60,
                70,
                60,
                60,
        };
        private final float density;

        public FamilyNexusAdapter(Context context) {
            familys = new NexusTypes[] {
                    new NexusTypes("Gioi"),
                    new NexusTypes("Kha"),
                    new NexusTypes("Trung Binh"),
                    new NexusTypes("Yeu")
            };

            density = context.getResources().getDisplayMetrics().density;

            for(int i = 0;i<=10;i++){
                familys[0].list.add(new Nexus("Nguyen Van A "+i, "9", "9", "9", "9", "9\"", "9","9"));
            }

            for(int i = 0;i<=10;i++){
                familys[1].list.add(new Nexus("Nguyen Van B "+i, "8", "8", "8", "8", "8\"", "8","8"));
            }
            for(int i = 0;i<=10;i++){
                familys[2].list.add(new Nexus("Nguyen Van C "+i, "5", "5", "5", "5", "5\"", "5","5"));
            }

            for(int i = 0;i<=10;i++){
                familys[3].list.add(new Nexus("Nguyen Van D "+i, "3", "3", "3", "3", "3\"", "3","3"));
            }

        }

        @Override
        public int getRowCount() {
            return 14;
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public View getView(int row, int column, View convertView, ViewGroup parent) {
            final View view;
            switch (getItemViewType(row, column)) {
                case 0:
                    view = getFirstHeader(row, column, convertView, parent);
                    break;
                case 1:
                    view = getHeader(row, column, convertView, parent);
                    break;
                case 2:
                    view = getFirstBody(row, column, convertView, parent);
                    break;
                case 3:
                    view = getBody(row, column, convertView, parent);
                    break;
                case 4:
                    view = getFamilyView(row, column, convertView, parent);
                    break;
                default:
                    throw new RuntimeException("wtf?");
            }
            return view;
        }

        private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
            return convertView;
        }

        private View getHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
            return convertView;
        }

        private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
            return convertView;
        }

        private View getBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
            return convertView;
        }

        private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
            }
            final String string;
            if (column == -1) {
                string = getFamily(row).name;
            } else {
                string = "";
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
            return convertView;
        }

        @Override
        public int getWidth(int column) {
            return Math.round(widths[column + 1] * density);
        }

        @Override
        public int getHeight(int row) {
            final int height;
            if (row == -1) {
                height = 35;
            } else if (isFamily(row)) {
                height = 25;
            } else {
                height = 45;
            }
            return Math.round(height * density);
        }

        @Override
        public int getItemViewType(int row, int column) {
            final int itemViewType;
            if (row == -1 && column == -1) {
                itemViewType = 0;
            } else if (row == -1) {
                itemViewType = 1;
            } else if (isFamily(row)) {
                itemViewType = 4;
            } else if (column == -1) {
                itemViewType = 2;
            } else {
                itemViewType = 3;
            }
            return itemViewType;
        }

        private boolean isFamily(int row) {
            int family = 0;
            while (row > 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return row == 0;
        }

        private NexusTypes getFamily(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return familys[family - 1];
        }

        private Nexus getDevice(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            family--;
            return familys[family].get(row + familys[family].size());
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }
    }
}
