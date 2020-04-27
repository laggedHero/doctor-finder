package net.laggedhero.doctorfinder.provider

class FakeStringProvider(
    private val creator: (Int) -> String
) : StringProvider {
    override fun getString(id: Int): String {
        return creator.invoke(id)
    }
}