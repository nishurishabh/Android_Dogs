package android.example.dogs.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.example.dogs.R
import android.example.dogs.viewModel.ListViewModel
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private var dogsListAdapter = DogListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }

        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            listErrror.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(viewLifecycleOwner, Observer { dogs ->
            dogs?.let {
                dogsList.visibility = View.VISIBLE
                dogsListAdapter.updateDogList(dogs)
            }
        })

        viewModel.dogsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listErrror.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listErrror.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionSetting -> {
                view?.let { Navigation.findNavController(it).navigate(ListFragmentDirections.actionsSettings()) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}