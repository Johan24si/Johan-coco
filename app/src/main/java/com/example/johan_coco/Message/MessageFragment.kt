package com.example.johan_coco.Message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.johan_coco.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menggunakan pravatar.cc yang lebih stabil dan cepat untuk testing
        val listMessage = listOf(
            MessageModel("Alya", "Halo! Apa kabar?", "https://i.pravatar.cc/150?u=alya"),
            MessageModel("Budi", "Sudah makan?", "https://i.pravatar.cc/150?u=budi"),
            MessageModel("Citra", "Jangan lupa tugasnya ya!", "https://i.pravatar.cc/150?u=citra"),
            MessageModel("Dika", "Besok kita rapat jam 9", "https://i.pravatar.cc/150?u=dika"),
            MessageModel("Eka", "Nice job kemarin!", "https://i.pravatar.cc/150?u=eka")
        )

        val adapter = MessageAdapter(requireContext(), listMessage)
        binding.listViewMessage.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}