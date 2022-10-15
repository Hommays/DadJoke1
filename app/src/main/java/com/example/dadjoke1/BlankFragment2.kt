package com.example.dadjoke1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import retrofit2.http.GET
import retrofit2.http.Headers

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: FragmentViewModel
    private val TAG = this::class.simpleName!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
        val randomJokeButton: Button = view.findViewById(R.id.buttonJoke)
        val randomJokeText: Text = view.findViewById(R.id.textOfJoke)
        randomJokeButton.setOnClickListener{
            viewModel.dadJokeService.singleRandomDadJoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ response, error ->
                    if (error!= null){
                        randomJokeText.textContent = response?.joke ?: getString(R.string.error_try_again)
                    }else{
                        randomJokeText.textContent = response?.joke ?: getString(R.string.cant_find_joke)
                    }
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentViewModel::class.java)
    }

}

data class DadJokeResponse(
    val id: String,
    val joke: String,
    val status: Int
)
interface DadJokeService{
    @GET("/")
    @Headers(
    )
    fun singleRandomDadJoke(): Single<DadJokeResponse>
}
