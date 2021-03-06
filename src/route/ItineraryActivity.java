package route;

import java.util.ArrayList;
import java.util.List;

import manager.ItineraryFileManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.c2dm.R;

public class ItineraryActivity extends Activity{
	
	private List<Address> enderecos;
	private List<String> enderecosSelecionados = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		ListView listEnderecos = (ListView) findViewById(R.id.listEnderecos);
		
		final ItineraryFileManager manager = new ItineraryFileManager(this);
		final List<String> enderecosSalvos = manager.getVisitedAddresses();
		String itineraryJSON = manager.getItineraryJSONFromFile();
		
		enderecos = manager.buildItineraryArray(itineraryJSON);
		
		ArrayAdapter<String> listEnderecosAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1) {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				String endereco = enderecos.get(position).toString();

				View view = convertView;
				
				if (view == null) {
					LayoutInflater inflater = getLayoutInflater();
					view = inflater.inflate(R.layout.item_endereco, null);
				}
				
				
				CheckBox checkboxEndereco = (CheckBox) view.findViewById(R.id.checkboxEndereco);
				checkboxEndereco.setTag(endereco);
				
				if(enderecosSalvos != null) {
					enderecosSelecionados = enderecosSalvos;
				}
				
				if (enderecosSelecionados.contains(endereco)) {
					checkboxEndereco.setChecked(true);
				} else {
					checkboxEndereco.setChecked(false);
				}
				
				checkboxEndereco.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CheckBox checkboxEndereco = (CheckBox) v;
						String endereco = (String) checkboxEndereco.getTag();
						
						if (checkboxEndereco.isChecked()) {
							Toast.makeText(getApplicationContext(), "Checkbox de " + endereco + " marcado!", Toast.LENGTH_SHORT).show();
							if (!enderecosSelecionados.contains(endereco)) {
								enderecosSelecionados.add(endereco);
							}
						} else {
							Toast.makeText(getApplicationContext(), "Checkbox de " + endereco + " desmarcado!", Toast.LENGTH_SHORT).show();
							if(enderecosSelecionados.contains(endereco)) {
								enderecosSelecionados.remove(endereco);
							}
						}
						manager.saveVisitedAddresses(enderecosSelecionados);
					}
				});
				
				TextView nomeEndereco = (TextView) view.findViewById(R.id.endereco);
				nomeEndereco.setText(endereco);
				
				return view;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public int getCount() {
				return enderecos.size();
			}
		};
		listEnderecos.setAdapter(listEnderecosAdapter);
	}
}
